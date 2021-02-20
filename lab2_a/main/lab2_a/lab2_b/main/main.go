package main
import (
	"fmt"
	"math/rand"
	"time"
)

var buffer []int = make([]int, 0)
var isRunningIvanov bool = true
var isRunningPetrov bool = true
var totalPrice=0
var stuff []int
var	truck []int

func Ivanov() {
	fmt.Println("Ivanov Start")
	for range stuff {
		stolenstuff := (stuff)[0]
		stuff = (stuff)[1:]
		buffer = append(buffer, stolenstuff)

		//fmt.Println("Stock: ", stuff, "\nBuffer: ", buffer)
	}
	isRunningIvanov=false

}

func Petrov() {
	fmt.Println("Petrov Start")
	for ; ; {
		if len(buffer)>0 {
			stolenstuff := (buffer)[0]
			buffer = (buffer)[1:]
			truck = append(truck, stolenstuff)

			//fmt.Println("", stolenstuff, " buffer: ", buffer, "\ntruck: ", truck)
		} else if isRunningIvanov{
			time.Sleep(time.Millisecond * 200)
			continue
		}else{
			isRunningPetrov=false
			break
		}
	}
}

func Necheporchuk(IsFinished chan int) {
	fmt.Println("Necheporchuk Start")
	for ; ; {
	if len(truck) > 0 {
		stolenstuff := (truck)[0]
			totalPrice += stolenstuff

			//fmt.Println("truck", truck[0], "\ntotalPrice =", totalPrice)
		truck = (truck)[1:]
		} else if isRunningPetrov{
			time.Sleep(time.Millisecond * 200)
		}else { break}
	}
	fmt.Println("\nTotal cost: ", totalPrice)
	IsFinished<- totalPrice
}
func startThreads( chanel chan int)  {
	go Ivanov()
	go Petrov()
	go Necheporchuk(chanel)
}


func main() {
	sizeOfStuff := 3
	IsFinished := make(chan int, 1)

	stuff = make([]int, sizeOfStuff)
	for i, _ := range stuff {
		s := rand.NewSource(time.Now().UnixNano())
		stuff[i]= (rand.New(s)).Intn(500)
	}

	fmt.Println("stuff price: ", stuff)
	startThreads(IsFinished)
	fmt.Println(<-IsFinished)
}


