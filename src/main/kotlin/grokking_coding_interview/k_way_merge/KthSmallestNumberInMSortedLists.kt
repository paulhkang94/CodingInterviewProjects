package grokking_coding_interview.k_way_merge

import java.util.PriorityQueue

fun main(args: Array<String>) {
    val input1: Array<IntArray> = arrayOf(intArrayOf(1))
    val k1 = 1
    printInput(input1, k1)
    println("$k1-th smallest element = ${KthSmallestNumberInMSortedLists.kthSmallest(input1, k1)}")

    val input2: Array<IntArray> = arrayOf(intArrayOf(1, 2))
    val k2 = 1
    printInput(input2, k2)
    println("$k2-th smallest element = ${KthSmallestNumberInMSortedLists.kthSmallest(input2, k2)}")

    val input3: Array<IntArray> = arrayOf(intArrayOf(1, 2))
    val k3 = 2
    printInput(input3, k3)
    println("$k3-th smallest element = ${KthSmallestNumberInMSortedLists.kthSmallest(input3, k3)}")

    val input4: Array<IntArray> = arrayOf(intArrayOf(2), intArrayOf(1))
    val k4 = 1
    printInput(input4, k4)
    println("$k4-th smallest element = ${KthSmallestNumberInMSortedLists.kthSmallest(input4, k4)}")

    val input5: Array<IntArray> = arrayOf(intArrayOf(1, 2), intArrayOf(1))
    val k5 = 1
    printInput(input5, k5)
    println("$k5-th smallest element = ${KthSmallestNumberInMSortedLists.kthSmallest(input5, k5)}")

    val input6: Array<IntArray> = arrayOf(intArrayOf(1, 2), intArrayOf(1, 2))
    val k6 = 2
    printInput(input6, k6)
    println("$k6-th smallest element = ${KthSmallestNumberInMSortedLists.kthSmallest(input6, k6)}")

    val input7: Array<IntArray> = arrayOf(intArrayOf(1, 2), intArrayOf(1, 2))
    val k7 = 3
    printInput(input7, k7)
    println("$k7-th smallest element = ${KthSmallestNumberInMSortedLists.kthSmallest(input7, k7)}")
}

private fun printInput(input: Array<IntArray>, k: Int) {
    println("k = $k")
    println("input = ")
    input.forEach {
        println(it.joinToString(", "))
    }
}

data class HeapElement(
    val value: Int,
    val arrayIdx: Int,
    val valueIdx: Int
)

object KthSmallestNumberInMSortedLists {

    fun kthSmallest(arrays: Array<IntArray>, k: Int): Int {
        if (arrays.size == 1) {
            return arrays[0][k - 1]
        }

        val minHeap = PriorityQueue<HeapElement> { a, b -> a.value - b.value }
        arrays.forEachIndexed { arrayIdx, array ->
            if (array.isNotEmpty()) {
                val heapElement = HeapElement(
                    value = array.first(),
                    arrayIdx = arrayIdx,
                    valueIdx = 0
                )
                minHeap.add(heapElement)
            }
        }

        for (i in 0 until k - 1) {
            val minElement = minHeap.poll()

            val array = arrays[minElement.arrayIdx]
            if (array.lastIndex > minElement.valueIdx) {
                val nextElement = HeapElement(
                    value = array[minElement.valueIdx + 1],
                    arrayIdx = minElement.arrayIdx,
                    valueIdx = minElement.valueIdx + 1
                )
                minHeap.add(nextElement)
            }

        }

        return minHeap.peek().value
    }
}

/*

Key Details
input: Array<IntArray> arrays, Int K
- 0 <= arrays.size <= l (arrays is finite size fits in memory)
- each array is sorted
- 0 <= array.size in array <= m (each array has finite size, fits in memory)
- 1 <= K <= n (total size of the entire array)
output: Kth smallest element in all of the arrays
- need to have a way of globally sorting all of the elements
- need a way to reference the Kth smallest element efficiently

Naive Solution
- put all of the elements into one large array -> O(n) time, O(n) space
- sort all of the elements -> O(nlogn time), O(n) space
- return Kth smallest element -> O(1)

Test Cases
- array = [[1]], k = 1 -> 1
- array = [[1 2]], k = 1 -> 1
- array = [[1 2]], k = 2 -> 2
- array = [[2], [1]], k = 1 -> 1
- array = [[1 2], [1]], k = 1 -> 1
- array = [[1 2], [1]], k = 2 -> 1
- array = [[1 2], [1]], k = 3 -> 2

Optimal Solution
- use max heap
-- whenever element is smaller than largest element (or heap is empty), add current element
-- if heap.size > k, pop the top (largest) element
-- at the end of adding all values, top element will be the Kth smallest element

Core Algorithm
1. validate input
- if (arrays.size == 1) return arrays[0][k - 1]

2. populate min heap with first value from each of the arrays
- val minHeap = PriorityQueue<Int> { a, b -> a - b }
- arrays.forEach { if (it.isNotEmpty()) minHeap.add(it[0]) }

3. iteratively remove the top (smallest) K - 1 elements, replacing with next element from the appropriate array
- for (i in 0 until k - 1)
-- val value = minHeap.poll()
-- if (value.next != null) minHeap.add(value.next)

4. return minHeap.peek()

Performance
time complexity: O((k - 1)logm)
space complexity: O(m)
 */