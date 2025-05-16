package view

import view.model.MenuViewModel

class MenuScreen(
    private val viewModel: MenuViewModel,
    private val homeScreen: HomeScreen? = null
) : BaseScreen {
    override fun display() {
        println("======== 메뉴 관리 ========")
        println("1. 메뉴확인 2. 메뉴추가 3. 메뉴수정 4. 메뉴삭제 0. 홈이동")
        println("==========================")
    }

    override fun handleInput() {
        when (readlnOrNull()) {
            "0" -> {
                println("==========================")
                println("          홈 이동          ")
                println("==========================")

                homeScreen?.let { ConsoleController.currentScreen = it }
            }
            "1" -> getAllMenu()
            else -> println("잘못된 입력")
        }
    }

    private fun getAllMenu() {
        println("\n======== 메뉴 목록 ========")

        val menuList = viewModel.getMenuList()

        if (menuList.isEmpty()) {
            println("등로된 메뉴가 없습니다.")
            println()
        } else {
            for (i in menuList.indices) {
                val menu = menuList[i]

                println("${i+1}. ${menu.name} - ${menu.price}원")
            }

            println("엔터키를 누르면 돌아갑니다.")
            readlnOrNull()
        }
    }
}