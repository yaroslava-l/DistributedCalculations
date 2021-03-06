package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)
func changeTicketPrice(rwLock *sync.RWMutex, graph *[][]int) {
	random := rand.New(rand.NewSource(time.Now().UnixNano()))
	for {
		rwLock.Lock()
		places := len(*graph)
		if places > 1 {
			first := random.Intn(places - 1)
			second := random.Intn(places - 1)
			newPrice := random.Intn(300) + 10
			changeNodes(newPrice, first, second, graph)
		}
		printGraph(graph)
		rwLock.Unlock()
		time.Sleep(1000 * time.Millisecond)
	}
}

func changeWays(rwLock *sync.RWMutex, graph *[][]int) {
	random := rand.New(rand.NewSource(time.Now().UnixNano()))
	for {
		rwLock.Lock()
		places := len(*graph)
		if places > 1 {
			var first = -1
			var second = -1
			if places == 2 {
				first = 0
				second = 1
			} else {
				for first == second {
					first = rand.Intn(places - 1)
					second = rand.Intn(places - 1)
				}
			}
			if (*graph)[first][second] == 0 {
				newPrice := random.Intn(300) + 10
				fmt.Println("Added ", first, " -> ", second, ". Price: ", newPrice)
				changeNodes(newPrice, first, second, graph)
			} else {
				changeNodes(0, first, second, graph)
				fmt.Println("Deleted ", first, " -> ", second)

			}
		}
		printGraph(graph)
		rwLock.Unlock()
		time.Sleep(1000 * time.Millisecond)
	}
}

func changeTowns(rwLock *sync.RWMutex, graph *[][]int) {
	for {
		rwLock.Lock()
		del := addOrDelete(graph)
		if del {
			for i := 0; i < len(*graph)-1; i++ {
				(*graph)[i] = (*graph)[i][:len((*graph)[i])-1]
			}
			*graph = (*graph)[:len(*graph)-1]
			fmt.Println(len(*graph)+1, " DELETED")
		} else {
			var newLine []int
			for i := 0; i < len(*graph); i++ {
				(*graph)[i] = append((*graph)[i], 0)
				newLine = append(newLine, 0)
			}
			newLine = append(newLine, 0)
			*graph = append(*graph, newLine)
			fmt.Println(len(*graph), " ADDED")
		}
		printGraph(graph)
		rwLock.Unlock()
		time.Sleep(1000 * time.Millisecond)
	}
}

func findWay(rwLock *sync.RWMutex, graph *[][]int) {
	for {
		rwLock.RLock()
		size := len(*graph)
		if size > 1 {
			var first = rand.Intn(size - 1)
			var second = rand.Intn(size- 1)
			if first != second {
				dfs(graph, first, second)
			}
		}
		rwLock.RUnlock()
		time.Sleep(1000 * time.Millisecond)
	}
}

func printGraph(graph *[][]int) {
	size := len(*graph)

	for i := 0; i < size; i++ {
		for j := 0; j < size; j++ {
			if (*graph)[i][j] == 0 {
				fmt.Print("0 ")
			} else {
				fmt.Print((*graph)[i][j], " ")
			}
		}
		fmt.Println()
	}
}

func changeNodes(newPrice, first, second int, graph *[][]int) {
	(*graph)[first][second] = newPrice
	(*graph)[second][first] = newPrice
	fmt.Println( first, " -> ", second, " New Price: ", newPrice)
}

func addOrDelete(graph *[][]int) bool {
	var del bool
	if len(*graph) < 3 {
		del = false
		return del
	}
	random := rand.New(rand.NewSource(time.Now().UnixNano()))

		if random.Intn(2) == 1 {
		del = true
	} else {
		del = false
	}

	return del
}

func dfs(graph *[][]int, first, second int) {
	size := len(*graph)
	distance := make([]int, size)
	visited := make([]int, size)
	vert := make([]int, size)
	parent := make([]int, size)

	for i := 0; i < size; i++ {
		distance[i] = -1
		visited[i] = -1
		vert[i] = -1
		parent[i] = -1
	}

	distance[first] = 0
	visited[first] = 0
	parent[first] = -1
	vert[0] = first

	i := 0
	realSize := 1
	for i < realSize {
		var tmp = vert[i]
		i++
		visited[tmp] = 1
		for i := 0; i < size; i++ {
			if (*graph)[tmp][i] != 0 {
				if visited[i] == -1 {
					parent[i] = tmp
					distance[i] = (*graph)[tmp][i] + distance[tmp]
					vert[realSize] = i
					realSize++
					visited[i] = 0
				} else if visited[i] == 0 {
					if distance[i] > (*graph)[tmp][i]+distance[tmp] {
						distance[i] = (*graph)[tmp][i] + distance[tmp]
						parent[i] = tmp
					}
				}
			}
		}
	}
	fmt.Println("SEARCHING PASS FROM ", first, " TO ", second)
	fmt.Print(second, " -> ")
	for parent[second] != -1 {
		second = parent[second]
		fmt.Print(second, " -> ")
	}
}

func main() {
	var rwLock sync.RWMutex
	var graph [][]int
	var waitGroup sync.WaitGroup

	waitGroup.Add(4)
	go changeTicketPrice(&rwLock, &graph)
	go changeWays(&rwLock, &graph)
	go changeTowns(&rwLock, &graph)
	go findWay(&rwLock, &graph)
	waitGroup.Wait()
}