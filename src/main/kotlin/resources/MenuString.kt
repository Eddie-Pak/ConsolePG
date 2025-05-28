package resources

object MenuStrings {
    const val TITLE = "======== 메뉴 관리 ========"
    const val MENU_OPTIONS = "1.메뉴확인 2.메뉴추가 3.메뉴수정 4.메뉴삭제 0.홈이동"
    const val RETURN_TO_MENU_MANAGEMENT = "메뉴관리"
    const val VALID_MENU_NUMBER_REQUIRED = "유효한 메뉴 번호를 입력해주세요."
    const val MENU_DISPLAY_FORMAT = "%s - %d원"
    const val MENU_LIST_DISPLAY_FORMAT = "%d. %s - %d원"

    // 메뉴 목록
    const val MENU_LIST_TITLE = "======== 메뉴 목록 ========"
    const val NO_MENU_AVAILABLE = "등록된 메뉴가 없습니다."

    // 메뉴 추가
    const val ADD_MENU_TITLE = "======== 메뉴 추가 ========"
    const val ENTER_MENU_NAME = "새로운 메뉴 이름: "
    const val ENTER_MENU_PRICE = "새로운 메뉴 가격: "
    const val MENU_NAME_REQUIRED = "메뉴이름을 올바르게 입력해주세요."
    const val VALID_PRICE_REQUIRED = "올바른 가격을 입력해주세요."
    const val MENU_ADDED_SUCCESS = "(%s - %d원) 을(를) 메뉴에 추가 하였습니다."

    // 메뉴 수정
    const val UPDATE_MENU_TITLE = "======== 메뉴 수정 ========"
    const val NO_MENU_TO_UPDATE = "수정할 메뉴가 없습니다."
    const val SELECT_MENU_TO_UPDATE = "수정할 메뉴 번호: "
    const val SELECTED_MENU = "선택한 메뉴: %s - %d원"
    const val ENTER_NEW_PRICE = "새 가격: "
    const val VALID_PRICE_INPUT_REQUIRED = "올바른 가격을 입력해 주세요."
    const val MENU_UPDATE_SUCCESS = "메뉴가 수정되었습니다."
    const val PRICE_CHANGE_FORMAT = "%s: %d원 -> %d원"

    // 메뉴 삭제
    const val DELETE_MENU_TITLE = "======== 메뉴 목록 ========"
    const val NO_MENU_TO_DELETE = "삭제할 메뉴가 없습니다."
    const val SELECT_MENU_TO_DELETE = "삭제할 메뉴 번호: "
    const val MENU_DELETE_CONFIRM = "%s 을(를) 삭제합니다."
    const val MENU_DELETE_SUCCESS = "%s 을(를) 삭제하였습니다."
    const val MENU_DELETE_ERROR = "삭제중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요."
}