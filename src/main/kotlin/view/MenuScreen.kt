package view

import domain.model.formatForDisplay
import navigate.Navigator
import navigate.ScreenType
import view.model.MenuViewModel

class MenuScreen(
    private val navigate: Navigator,
    private val viewModel: MenuViewModel
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

                navigate.navigateTo(ScreenType.Home)
            }

            "1" -> getAllMenu()

            "2" -> addMenu()

            "3" -> updateMenu()

            "4" -> deleteMenu()

            else -> println("잘못된 입력")
        }
    }

    private fun getAllMenu() {
        println("\n======== 메뉴 목록 ========")

        val menuList = viewModel.getMenuList()

        if (menuList.isEmpty()) {
            println("등록된 메뉴가 없습니다.")
            println()
        } else {
            println(menuList.formatForDisplay())
        }

        print("\n엔터키를 누르면 메뉴관리로 돌아갑니다.")
        readlnOrNull()
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

    private fun updateMenu() {
        println("\n======== 메뉴 수정 ========")
        println()

        val menuList = viewModel.getMenuList()

        if (menuList.isEmpty()) {
            println("수정할 메뉴가 없습니다.")
            println()
        } else {
            println(menuList.formatForDisplay())

            try {
                print("\n수정할 메뉴 번호: ")
                val menuIndex = readlnOrNull()?.toIntOrNull() ?: 0
                require(menuIndex in 1..menuList.size) {"유효한 메뉴 번호를 입력해주세요."}

                val selectedMenu = menuList[menuIndex - 1]

                println("\n선택한 메뉴: ${selectedMenu.name} - ${selectedMenu.price}원")

                print("새 가격: ")
                val newPrice = readlnOrNull()?.toIntOrNull()
                require(newPrice != null && newPrice > 0) {"올바른 가격을 입력해 주세요."}

                val updateMenu = viewModel.updateMenu(selectedMenu.id, newPrice)

                println("\n메뉴가 수정되었습니다.")
                println("${updateMenu.name}: ${selectedMenu.price}원 -> ${updateMenu.price}원")

            } catch (e: Exception) {
                println("오류: ${e.message}")
            }
        }

        print("\n엔터키를 누르면 메뉴관리로 돌아갑니다.")
        readlnOrNull()
    }

    private fun deleteMenu() {
        println("\n======== 메뉴 목록 ========")
        println()

        val menuList = viewModel.getMenuList()

        if (menuList.isEmpty()) {
            println("삭제할 메뉴가 없습니다.")
            println()
        } else {
            println(menuList.formatForDisplay())

            try {
                print("\n삭제할 메뉴 번호:")
                val menuIndex = readlnOrNull()?.toIntOrNull() ?: 0
                require(menuIndex in 1..menuList.size) {"유효한 번호를 입력하세요."}

                val selectMenu = menuList[menuIndex - 1]

                println("${selectMenu.name} 을(를) 삭제합니다.")
                println("삭 제 중.......")

                if (viewModel.deleteMenu(selectMenu.id)) {
                    println("\n${selectMenu.name} 을(를) 삭제하였습니다.")
                }

            } catch (e: Exception) {
                println("오류: ${e.message}")
            }

            print("\n엔터키를 누르면 메뉴관리로 돌아갑니다.")
            readlnOrNull()
        }
    }
}