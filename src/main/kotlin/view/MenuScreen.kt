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

            "2" -> addMenu()

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

            print("\n엔터키를 누르면 메뉴관리로 돌아갑니다.")
            readlnOrNull()
        }
    }

    private fun addMenu() {
        println("\n======== 메뉴 추가 ========")

        try {
            print("메뉴이름을 입력해주세요: ")
            val menuName = readlnOrNull() ?: ""
            require(menuName.isNotEmpty()) {"\n메뉴이름을 입력해주세요.\n"}

            print("메뉴가격을 입력해주세요: ")
            val price = readlnOrNull()?.toIntOrNull()
            require(price != null && price > 0) {"\n올바른 가격을 입력해주세요.\n"}

            val addMenu = viewModel.addMenu(menuName, price)

            println("${addMenu.name}을(를) 메뉴에 추가 하였습니다.")
            println()
        } catch (e: Exception) {
            println("오류: ${e.message}")
        }

        print("엔터키를 누르면 메뉴관리로 돌아갑니다.")
        readlnOrNull()
    }
}