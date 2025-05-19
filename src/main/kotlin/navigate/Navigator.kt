package navigate

import view.BaseScreen

interface Navigator {
    fun navigateTo(screenType: ScreenType)

    fun getCurrentScreen(): BaseScreen
}