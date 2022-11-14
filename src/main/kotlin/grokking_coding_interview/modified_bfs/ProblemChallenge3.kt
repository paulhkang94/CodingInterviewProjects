package grokking_coding_interview.modified_bfs


fun main(args: Array<String>) {
    val input1 = intArrayOf(1, 2, 3)
    print(input1)
    println("Rotation count = ${ProblemChallenge3.findRotationCount(input1)}")

    val input2 = intArrayOf(10, 15, 1, 3, 8)
    print(input2)
    println("Rotation count = ${ProblemChallenge3.findRotationCount(input2)}")

    val input3 = intArrayOf(4, 5, 7, 9, 10, -1, 2)
    print(input3)
    println("Rotation count = ${ProblemChallenge3.findRotationCount(input3)}")

    val input4 = intArrayOf(1, 3, 8, 10)
    print(input4)
    println("Rotation count = ${ProblemChallenge3.findRotationCount(input4)}")
}

fun print(input: IntArray) {
    print("input: ")
    input.forEach { print("$it, ") }
    println("")
}

object ProblemChallenge3 {
    //Given an array of numbers which is sorted in ascending order and is rotated ‘k’ times around a pivot, find ‘k’.
    //
    //You can assume that the array does not have any duplicates.

    fun findRotationCount(nums: IntArray): Int {
        if (nums.size <= 1) {
            return 0
        }

        var startIdx = 0
        var endIdx = nums.lastIndex
        var midIdx = startIdx + (endIdx - startIdx)/2

        if (nums[startIdx] <= nums[midIdx] && nums[midIdx] <= nums[endIdx]) {
            return 0
        }

        while (startIdx < endIdx) {
            midIdx = startIdx + (endIdx - startIdx)/2

            if (nums[startIdx] <= nums[midIdx]) {
                // left side is in order, right side contains pivot
                startIdx = midIdx + 1
            } else {
                // right side is in order, left side contains pivot
                endIdx = midIdx
            }
        }

        return startIdx
    }
}
/*
Key Details
- input: Integer array nums
-- may or may not be rotated to the right by 'x' indices
- output: number of indices that nums is rotated
-- minimum value of 0 (no rotations)
-- maximum value of n - 1

Test Cases
- nums.size < 1 -> 0
- [1 2] -> 0
- [2 1] -> 1
- [1 2 3] -> 0
- [3 1 2] -> 1
- [2 3 1] -> 2

Core Algorithm
- validate input
-- if (nums.size <= 1) return 0

- check if array is rotated at all
-- if value at start <= value at middle <= value at the end -> array is not rotated, return 0

- find index where array is rotated using binary search
-- var startIdx = 0, endIdx = nums.lastIndex, midIdx = 0
-- while (startIdx < endIdx)
--- midIdx = startIdx + (endIdx - startIdx)/2
--- if (value at startIdx <= value at midIdx) -> left side is sorted, right side contains the pivot point
---- startIdx = midIdx + 1
--- else left side contains the pivot point
---- endIdx = midIdx
-- return startIdx (pivot point index)

Performance
- time complexity: O(logn)
- space complexity: O(1)

 */
