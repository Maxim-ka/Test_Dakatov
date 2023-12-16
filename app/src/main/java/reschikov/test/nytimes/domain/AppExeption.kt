package reschikov.test.nytimes.domain

sealed class AppException : Throwable(){
    object NoInternet : AppException()
}
