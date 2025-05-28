package resources

object StoreStrings {
    const val TITLE = "======== 메장 관리 ========"
    const val MENU_OPTIONS = "1.주문확인 2.주문하기 3.주문수정 0.홈이동"
    const val RETURN_TO_STORE_MANAGEMENT = "매장관리"
    const val NO_ORDERS_MESSAGE = "현재 모든 테이블에 주문이 없습니다."
    const val TABLE_ORDER_HEADER = "=%d번 테이블="
    const val TABLE_TOTAL_PRICE = "총가격: %d원"

    // 주문 확인
    const val ORDER_CHECK_TITLE = "======== 주문 확인 ========"

    // 주문 하기
    const val ADD_ORDER_TITLE = "======== 주문 하기 ========"
    const val NO_MENU_TO_ORDER = "주문 가능한 메뉴가 없습니다."
    const val MENU_LIST_TITLE = "=== 메뉴 목록 ==="
    const val ORDER_INPUT_INSTRUCTION = "주문을 입력해주세요. 형식: 메뉴번호-수량 (예: 1-2, 3-1)"
    const val ORDER_PROMPT = "%d번 테이블 주문: "
    const val ORDER_INPUT_REQUIRED = "주문을 입력해주세요."
    const val VALID_ORDER_FORMAT_REQUIRED = "올바른 주문 형식을 입력해주세요."
    const val ORDER_COMPLETE_TITLE = "=== 주문이 완료되었습니다 ==="
    const val TABLE_ORDER_INFO = "테이블 번호: %d번 주문내용"
    const val TABLE_TOTAL_AMOUNT = "테이블 총 금액: %d원"
    const val VALID_ORDER_FORMAT_MESSAGE = "메뉴를 올바른형식으로 입력해주세요."
    const val VALID_MENU_RANGE = "유효한 메뉴 번호(1-%d)를 입력해주세요."
    const val QUANTITY_MIN_REQUIRED = "수량은 1 이상이어야 합니다."

    // 주문 수정
    const val UPDATE_ORDER_TITLE = "======== 주문 수정 ========"
    const val NO_UNPAID_ORDER = "%d번 테이블에 미결제 주문이 없습니다."
    const val CURRENT_ORDER_TITLE = "=== %d번 테이블 현재 주문 ==="
    const val MENU_LIST_DISPLAY_FORMAT = "%d. %s - %d개"
    const val MENU_DISPLAY_FORMAT = "%s - %d개"
    const val SELECT_MENU_TO_UPDATE = "수정할 메뉴 번호 (1~%d): "
    const val VALID_MENU_NUMBER = "유효한 메뉴 번호를 입력해주세요."
    const val SELECTED_MENU_INFO = "선택한 메뉴: %s (현재 수량: %d개)"
    const val NEW_QUANTITY_PROMPT = "새로운 수량 (0 입력시 삭제): "
    const val VALID_QUANTITY_INPUT = "올바른 수량을 입력해주세요. (0 이상)"
    const val ALL_ORDERS_CANCELLED = "%d번 테이블의 모든 주문이 취소되었습니다."
    const val ORDER_UPDATE_SUCCESS = "=== 주문이 수정되었습니다 ==="
    const val MENU_REMOVED = "%s이(가) 주문에서 제거되었습니다."
    const val QUANTITY_CHANGED = "%s: %d개 → %d개"
    const val UPDATED_ORDER_TITLE = "=== 수정된 주문 내역 ==="
    const val NEW_TOTAL_AMOUNT = "새로운 총 금액: %d원"
}