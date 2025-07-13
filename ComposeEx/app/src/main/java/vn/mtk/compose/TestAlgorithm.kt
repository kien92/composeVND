package vn.mtk.compose

object TestAlgorithm {
    /**
     * Kiểm tra xem mảng [numbers] có thể được chia thành hai tập con mà
     * tổng các phần tử của hai tập con đó bằng nhau hay không.
     *
     * @param numbers mảng số nguyên (giả định không âm).
     * @return true nếu có thể, false nếu không.
     */
    fun canPartitionIntoEqualSum(numbers: IntArray): Boolean {
        // 1. Tính tổng các phần tử
        val totalSum = numbers.sum()

        // Nếu tổng lẻ, không thể chia đôi
        if (totalSum % 2 != 0) {
            return false
        }

        val target = totalSum / 2

        // 2. Dùng động quy 1 chiều: dp[s] = true nếu có thể lấy một số phần tử
        //    nào đó từ đầu mảng để tổng bằng s.
        val dp = BooleanArray(target + 1).also { it[0] = true }

        // 3. Duyệt từng số trong mảng, cập nhật dp từ phải sang trái để tránh dùng
        //    số vừa xét nhiều lần trong cùng một vòng lặp.
        for (num in numbers) {
            if (num > target) continue

            for (sum in target downTo num) {
                if (dp[sum - num]) {
                    dp[sum] = true
                }
            }
        }

        return dp[target]
    }
}