import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFile {
    private String databaseName;
    DatabaseFile(String databaseName){
        this.databaseName = databaseName;
    }

    public void removeLines( int startLine, int numLines) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(databaseName));
            StringBuilder sb = new StringBuilder("");
            int linenumber = 0;
            String line;

            while ((line = br.readLine()) != null) {
                if (linenumber < startLine || linenumber >= startLine + numLines)
                    sb.append(line).append("\n");
                linenumber++;
            }
            if (startLine + numLines > linenumber)
                System.out.println("End of file.");
            br.close();

            FileWriter fw = new FileWriter(new File(databaseName));
            fw.write(sb.toString());
            fw.close();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}
class DatabaseController {
    private DatabaseFile database;
    private ReadWriteController readWriteLock = new ReadWriteController();

    public DatabaseController(DatabaseFile database) {
        this.database = database;
    }

    public List<String> getPhoneNumbers(String username) {
        try {
            readWriteLock.readLock();
            List<String> res = new ArrayList<>();
            BufferedReader fileReader = new BufferedReader(new FileReader(String.valueOf(database)));
            String line = fileReader.readLine();
            while (line != null) {
                if ((line.split("\\s+")[0]).equals(username))
                    res.add(line.split("\\s+")[1]);
                line = fileReader.readLine();
            }
            System.out.println("Found Phone Number: " + res.toString() + " for user: " + username);
            return res;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readUnlock();
        }
        System.out.println("Found no phone number for: " + username);
        return null;
    }

    public String getUsername(String phoneNumber) {
        try {
            readWriteLock.readLock();
            BufferedReader fileReader = new BufferedReader(new FileReader(String.valueOf(database)));
            String line = fileReader.readLine();
            while (line != null) {
                if ((line.split("\\s+")[1]).equals(phoneNumber)) {
                    String user = line.split("\\s+")[0];
                    System.out.println("Found Username: " + user + " by phone: " + phoneNumber);
                    return user;
                }
                line = fileReader.readLine();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readUnlock();
        }
        System.out.println("Found no Username with phone: " + phoneNumber);
        return null;
    }


    public void addRecord(String username, String phoneNumber) {
        PrintWriter pw = null;
        try {
            readWriteLock.writeLock();
            pw = new PrintWriter(new BufferedWriter(new FileWriter(String.valueOf(database), true)));
            pw.println(username + " " + phoneNumber);
            System.out.println("Adding: " + username + " " + phoneNumber);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            pw.close();
            readWriteLock.writeUnlock();
        }
    }

    public void deleteRecord(String username, String phoneNumber) {
        try {
            readWriteLock.writeLock();
            BufferedReader reader = new BufferedReader( new FileReader(String.valueOf(database)));
            String curr;
            String remove = username + " " + phoneNumber;
            int cnt = 0;
            while ((curr = reader.readLine()) != null) {
                String trimmedLine = curr.trim();
                if (trimmedLine.equals(remove)) break;
                cnt++;
            }
            reader.close();
            if (curr != null) {
                System.out.println("Removing: " + username + " " + phoneNumber);
                database.removeLines(cnt, 1);
            } else System.out.println("Found no user: " + username + " " + phoneNumber);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeUnlock();
        }
    }

}