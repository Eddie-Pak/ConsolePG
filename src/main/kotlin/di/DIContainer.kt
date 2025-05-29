package di

object DIContainer {
    private val _provider: DiProvider = DIProviderImpl()

    val provider: DiProvider get() = _provider
}