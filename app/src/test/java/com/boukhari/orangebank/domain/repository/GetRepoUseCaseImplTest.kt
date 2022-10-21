package com.boukhari.orangebank.domain.repository

import com.boukhari.orangebank.domain.repository.model.Repo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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
    fun test_happy_flow() = runBlocking {
        //GIVEN
        val fakeRepo1 = Repo(1, "name", description = "description")
        val fakeRepo2 = fakeRepo1.copy(id = 2)
        coEvery { repository.getRepos() } returns listOf(fakeRepo1, fakeRepo2)

        //WHEN
        val collectedValues = mutableListOf<Resource<List<Repo>>>()
        getRepoUseCase.getRepos().toList(collectedValues)
        val expectedData = listOf(fakeRepo1, fakeRepo2)

        //THEN
        //assert is Loading is emitted first
        assertTrue(collectedValues[0] is Resource.Loading)
        //assert data is emitted with emptyList
        assertTrue(collectedValues[1] is Resource.Success)
        assertEquals(collectedValues[1].data, expectedData)
    }


    @Test
    fun test_useCase_returns_emptyList() = runBlocking {
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
    fun test_useCase_returns_error() = runBlocking {
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
}