package com.boukhari.orangebank.domain.repository

import com.boukhari.orangebank.domain.GetRepoUseCaseImpl
import com.boukhari.orangebank.domain.RepoRepository
import com.boukhari.orangebank.domain.Resource
import com.boukhari.orangebank.domain.model.Repo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class GetRepoUseCaseImplTest {

    @MockK
    lateinit var repository: RepoRepository

    @InjectMockKs
    lateinit var getRepoUseCase: GetRepoUseCaseImpl

    @Before
    fun setUp() =
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks

    @Test
    fun test_getRepos_happy_flow_with_refresh() = runBlocking {
        //GIVEN
        val fakeRepo1 = Repo(1, "name", description = "description")
        val fakeRepo2 = fakeRepo1.copy(id = 2)
        coEvery { repository.getRepos() } returns listOf(fakeRepo1, fakeRepo2)

        //WHEN
        val collectedValues = mutableListOf<Resource<List<Repo>>>()
        getRepoUseCase.getRepos(true).toList(collectedValues)
        val expectedData = listOf(fakeRepo1, fakeRepo2)

        //THEN
        //ensure downloadRepos is called before getRepos
        coVerifyOrder {
            repository.downloadRepos()
            repository.getRepos()
        }
        //assert is Loading is emitted first
        assertTrue(collectedValues[0] is Resource.Loading)
        //assert data is emitted with emptyList
        assertTrue(collectedValues[1] is Resource.Success)
        assertEquals(collectedValues[1].data, expectedData)
    }

    @Test
    fun test_getRepos_happy_flow() = runBlocking {
        //GIVEN
        val fakeRepo1 = Repo(1, "name", description = "description")
        val fakeRepo2 = fakeRepo1.copy(id = 2)
        coEvery { repository.getRepos() } returns listOf(fakeRepo1, fakeRepo2)

        //WHEN
        val collectedValues = mutableListOf<Resource<List<Repo>>>()
        getRepoUseCase.getRepos(false).toList(collectedValues)
        val expectedData = listOf(fakeRepo1, fakeRepo2)

        //THEN
        //verify that download is never called
        coVerify(exactly = 0) { repository.downloadRepos() }
        coVerify(exactly = 1) { repository.getRepos() }
        //assert is Loading is emitted first
        assertTrue(collectedValues[0] is Resource.Loading)
        //assert data is emitted with emptyList
        assertTrue(collectedValues[1] is Resource.Success)
        assertEquals(collectedValues[1].data, expectedData)
    }

    @Test
    fun test_getRepos_returns_emptyList() = runBlocking {
        //GIVEN
        coEvery { repository.getRepos() } returns emptyList()

        //WHEN
        val collectedValues = mutableListOf<Resource<List<Repo>>>()
        getRepoUseCase.getRepos().toList(collectedValues)
        val expectedData = emptyList<Repo>()

        //THEN
        //assert is Loading is emitted first
        assertTrue(collectedValues[0] is Resource.Loading)
        //assert data is emitted with emptyList
        assertTrue(collectedValues[1] is Resource.Success)
        assertEquals(collectedValues[1].data, expectedData)
    }

    @Test
    fun test_getRepos_returns_error() = runBlocking {
        //GIVEN
        val fakeErrorResponse = Response.error<Any>(401, byteArrayOf().toResponseBody())
        coEvery { repository.getRepos() } throws HttpException(fakeErrorResponse)

        //WHEN
        val collectedValues = mutableListOf<Resource<List<Repo>>>()
        getRepoUseCase.getRepos().toList(collectedValues)

        //THEN
        //assert is Loading is emitted first
        assertTrue(collectedValues[0] is Resource.Loading)
        //assert error is emitted and has message
        assertTrue(collectedValues[1] is Resource.Error)
        assertFalse(collectedValues[1].message.isNullOrEmpty())
    }

    @Test
    fun test_getRepoById_returns_item() = runBlocking {
        //GIVEN
        val fakeRepo1 = Repo(1, "name", description = "description")
        coEvery { repository.getRepoById(any()) } returns fakeRepo1

        //WHEN
        val collectedValues = mutableListOf<Resource<Repo>>()
        getRepoUseCase.getRepoById(1).toList(collectedValues)

        //THEN
        //assert function returns the right item
        assertTrue(collectedValues[0] is Resource.Success)
        assertEquals(collectedValues[0].data, fakeRepo1)
    }

    @Test
    fun test_getRepoById_returns_no_item() = runBlocking {
        //GIVEN
        coEvery { repository.getRepoById(any()) } returns null

        //GIVEN
        val collectedValues = mutableListOf<Resource<Repo>>()
        getRepoUseCase.getRepoById(1).toList(collectedValues)

        //THEN
        //assert error is emitted and has message
        assertTrue(collectedValues[0] is Resource.Error)
        assertFalse(collectedValues[0].message.isNullOrEmpty())
    }
}