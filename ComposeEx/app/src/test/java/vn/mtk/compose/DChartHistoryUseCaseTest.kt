package vn.mtk.compose

class ExampleRepositoryTest {
    // Mock repository/interface
    private val repo: MyRepository = mockk()

    @Test
    fun `test suspend function with MockK`() = runTest {
        // Arrange: mock suspend function
        coEvery { repo.getData("param") } returns listOf(1, 2, 3)

        // Act
        val result = repo.getData("param")

        // Assert
        assertEquals(listOf(1, 2, 3), result)
        coVerify(exactly = 1) { repo.getData("param") }
    }
}