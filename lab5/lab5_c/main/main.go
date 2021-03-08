package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var random = rand.New(rand.NewSource(time.Now().UnixNano()))

type CyclicBarrier struct {
	id int
	count int
	parties int
	finishState chan bool
	trip *sync.Cond
	array1 *[]int
	array2 *[]int
	array3 *[]int
}

func (b *CyclicBarrier) nextBarrier() {
	fmt.Println("Barrier", b.id)
	state := check(b.array1,b.array2,b.array3)
	for i := 0; i < b.parties; i++ {
		b.finishState <- state
	}
	b.trip.Broadcast()
	b.count = b.parties
	b.id++
}

func (b *CyclicBarrier) Await() {
	b.trip.L.Lock()
	defer b.trip.L.Unlock()
	barrier := b.id
	b.count--
	index := b.count

	if index == 0 {
		b.nextBarrier()
	} else {
		for barrier == b.id {
			b.trip.Wait()
		}
	}
}

func NewCyclicBarrier(num int, array1 *[]int, array2 *[]int, array3 *[]int) *CyclicBarrier {
	b := CyclicBarrier{}
	b.count = num
	b.parties = num
	b.trip = sync.NewCond(&sync.Mutex{})
	b.finishState = make(chan bool, 3)
	b.array1 = array1
	b.array2 = array2
	b.array3 = array3
	return &b
}

func arrayControl(id int, array *[]int, barrier *CyclicBarrier, waitGroup *sync.WaitGroup) {
	defer (*waitGroup).Done()
	for {
		element := random.Intn(len(*array))
		if random.Intn(2)%2 == 0 && (*array)[element] > 0 {
			(*array)[element]--
		} else if (*array)[element] < 10 {
			(*array)[element]++
		}
		fmt.Println("Thread ", id, " modified array:", *array, "Sum = ", getSum(array))
		barrier.Await()
		finish := <-barrier.finishState
		if finish {
			fmt.Println("Thread #", id, " finished")
			break
		}
	}
}

func randArray(size int) []int {
	array := make([]int, size)
	for i := range array {
		array[i] = random.Intn(10)
	}
	return array
}

func check(array1, array2,array3 *[]int)  bool {
	sum1 := getSum(array1)
	sum2 := getSum(array2)
	sum3 := getSum(array3)

	if sum1 == sum2 && sum2 == sum3 {
		return true
	}
	return false
}

func getSum(array *[]int) int {
	sum := 0
	for i := range *array {
		sum += (*array)[i]
	}
	return sum
}

func main() {
	waitGroup := sync.WaitGroup{}
	array1 := randArray(5)
	array2 := randArray(5)
	array3 := randArray(5)

	fmt.Println("Array 1: ", array1)
	fmt.Println("Array 2: ", array2)
	fmt.Println("Array 3: ", array3)

	barrier := NewCyclicBarrier(3, &array1, &array2, &array3)

	waitGroup.Add(3)
	go arrayControl(1, &array1, barrier, &waitGroup)
	go arrayControl(2, &array2, barrier, &waitGroup)
	go arrayControl(3, &array3, barrier, &waitGroup)

	waitGroup.Wait()
}