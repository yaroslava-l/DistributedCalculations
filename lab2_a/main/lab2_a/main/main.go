package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var (
	n        = 5
	m        = 10
	beeGroup = 3
	)


func setBearPosition(matrix [][]bool) {
	x := rand.New(rand.NewSource(time.Now().UnixNano())).Intn(n)
	y :=  rand.New(rand.NewSource(time.Now().UnixNano())).Intn(n)
	matrix[x][y] = true
}


func findBear(id int, waitGroup *sync.WaitGroup, regions <-chan []bool, isbearFound chan bool) {

	for region := range regions {
		select {
		case <-isbearFound:
			isbearFound<- true
			return
		default:
			fmt.Println("Bees group", id, "searching for bear in region ", region)

			for index, i := range region {
				if i {
					fmt.Println("No No No. Bees group", id, "found the bear on", index+1,"position")
					isbearFound <- true
					fmt.Println("Bees group", id, "returned.")
					waitGroup.Done()
					return
				}
			}
		}
		fmt.Println("Bees group", id, "returned.")
	}
	waitGroup.Done()
}

func main() {

	forest := make([][]bool, n)
	for i := range forest {
		forest[i] = make([]bool, m)
	}

	setBearPosition(forest)
	for i := range forest{
		fmt.Println(forest[i])
	}

	findersgroups := make(chan []bool, n)
	bearFounder := make(chan bool, 1)

	var paralGroup sync.WaitGroup

	for i := 0; i < beeGroup; i++ {
		paralGroup.Add(1)
		go findBear(i, &paralGroup, findersgroups, bearFounder)
	}

	for i := 0; i < n; i++ {
		findersgroups <- forest[i]
	}

	close(findersgroups)
	paralGroup.Wait()
}
