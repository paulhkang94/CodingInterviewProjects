package grokking_coding_interview.top_k_elements

import java.util.PriorityQueue

fun main(args: Array<String>) {
    var array = intArrayOf(1, 3, 12, 5, 15, 11)
    var k1 = 3
    var k2 = 6
    printInputs(array, k1, k2)

    var output = SumOfElements().sumOfElementsMaxHeap(array, k1, k2)
    printOutput(output) // expect 23

    array = intArrayOf(3, 5, 8, 7)
    k1 = 1
    k2 = 4
    printInputs(array, k1, k2)

    output = SumOfElements().sumOfElementsMaxHeap(array, k1, k2)
    printOutput(output) // expect 12
}

/*

Key Details
input: IntArray array, int k1, int k2
- 1 <= k1 <= k2 <= array.size
- 0 <= array.size <= n
- elements are not sorted
- can have duplicate elements
output: sum of all the elements between the k1th and k2th smallest elements

Test Cases
- arr.isEmpty() -> 0
- (k2 - k1 <= 1) -> 0
- arr = [1 3 2], k1 = 1, k2 = 3 -> 2
- arr = [1 4 3 2], k1 = 1, k2 = 4 -> 5

- Initial Solution
-- sort the elements -> O(nlogn) time complexity, O(n) space complexity
-- from idx (k1 - 1) to idx (k2 - 1), add the sum of numbers together
-- return the total sum

Core Algorithm
- validate input
-- if (arr.isEmpty() || (k2 - k1 <= 1)) return 0
-- if (k1 == 1 && k2 == arr.size) return sum of all elements in the array

- sort the elements
- var sum = 0
- for (i in k1 until k2 - 1) sum += array[i]

- return sum

Performance
time complexity: O(nlogn + n) = O(nlogn)
space complexity: O(n + n) = O(n)

Alternate Solution - use heap
- add all elements to min heap -> top element is always the smallest
- remove the first k1 elements from the heap
- add all elements between k1 and k2
-

 */

private fun printInputs(
    array: IntArray,
    k1: Int,
    k2: Int
) {
    println("Input array = ${array.contentToString()}")
    println("k1 = $k1, k2 = $k2")
}

private fun printOutput(
    sumOfElements: Int
) {
    println("Output sum of elements = $sumOfElements")
}

/*
Given an array, find the sum of all numbers between the K1’th and K2’th smallest elements of that array.
 */

class SumOfElements {
    fun sumOfElements(
        arr: IntArray,
        k1: Int,
        k2: Int
    ): Int {
        if (arr.isEmpty() || (k2 - k1 <= 1)) {
            return 0
        }

        var sum = 0

        arr.sort()
        for (i in k1 until k2 - 1) {
            sum += arr[i]
        }

        return sum
    }

    fun sumOfElementsMinHeap(
        arr: IntArray,
        k1: Int,
        k2: Int
    ): Int {
        val minHeap = PriorityQueue<Int> { a, b -> a - b }
        arr.forEach { minHeap.offer(it) }

        var sum = 0
        for (i in 1 .. k1) {
            minHeap.poll()
        }

        for (i in k1 + 1 until k2) {
            sum += minHeap.poll()
        }

        return sum
    }

    fun sumOfElementsMaxHeap(
        arr: IntArray,
        k1: Int,
        k2: Int
    ): Int {
        val maxHeap = PriorityQueue<Int> { a, b -> b - a }
        arr.forEach {
            maxHeap.add(it)

            if (maxHeap.size >= k2) {
                maxHeap.poll()
            }
        }

        var sum = 0

        for (i in 0 until (k2 - k1 - 1)) {
            sum += maxHeap.poll()
        }

        return sum
    }
}