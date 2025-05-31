package resources

object SalesStrings {
    const val TITLE = "======== 메출 관리 ========"
    const val MENU_OPTIONS = "1.결제하기 2.매출확인 0. 홈이동"
    const val RETURN_TO_SALES_MANAGEMENT = "매출관리"
    const val INVALID_INPUT_TRY_AGAIN = "잘못된 입력입니다. 다시 선택해주세요."

    // 결제 하기
    const val PAYMENT_TITLE = "======== 결제 하기 ========"
    const val NO_ORDERS_TO_PAY = "결제할 주문이 없습니다."
    const val TABLES_WITH_ORDERS = "=== 주문이 있는 테이블 목록 ==="
    const val TABLE_HEADER = "  =%d번 테이블="
    const val MENU_BODY = "  %s - %d개"
    const val TOTAL_AMOUNT = "  총 액: %d원"
    const val SELECT_TABLE_TO_PAY = "결제할 테이블번호: "
    const val NO_ORDER_FOR_TABLE = "%d번 테이블에 결제할 주문이 없습니다."
    const val TABLE_ORDER_DETAILS = "=== %d번 테이블 주문 내역 ==="
    const val MENU_NAME_AMOUNT = "%s - %d개"
    const val TOTAL_PAYMENT_AMOUNT = "총 결제 금액: %d원"
    const val PAYMENT_QUESTION = "결제를 진행하시겠습니까? (Y/N): "
    const val PAYMENT_CANCEL = "결제가 취소되었습니다."
    const val PAYMENT_COMPLETE_TITLE = "=== 결제가 완료되었습니다 ==="
    const val PAYMENT_TABLE = "테이블: %d번"
    const val PAYMENT_AMOUNT = "결제 금액: %d원"
    const val PAYMENT_TIME = "결제 시간: %s"

    // 매출 확인
    const val SALES_REPORT_TITLE = "======== 매출 확인 ========"
    const val SALES_REPORT_OPTIONS = "1.월별매출 2.일별매출 3.메뉴별매출"

    // 월별 매출
    const val MONTHLY_SALES_TITLE = "======== %d년 월별 매출 (1월~%d월) ========"
    const val NO_YEARLY_SALES_DATA = "%d년 매출 데이터가 없습니다."
    const val MONTH_SALES = "%d월: %s원"
    const val TOTAL_YEAR_SALES = "%d년 %d월 까지 총 매출: %s원"

    // 일별 매출
    const val DAILY_SALES_TITLE = "======== 일별 매출 조회 ========"
    const val AVAILABLE_MONTHS = "조회 가능한 월: 1월 ~ %d월"
    const val SELECT_MONTH_PROMPT = "조회할 월을 입력하세요 (1~%d): "
    const val MONTH_RANGE_ERROR = "1월부터 %d월 까지만 선택 가능합니다."
    const val DAILY_SALES_HEADER = "======== %d년 %d월 일별 매출 ========"
    const val NO_MONTHLY_SALES_DATA = "%d년 %d월 매출 데이터가 없습니다."
    const val DAILY_SALES_FORMAT = "%d일: %s원"
    const val MONTHLY_TOTAL_SALES = "%d년 %d월 총 매출: %s원"

    // 메뉴별 매출
    const val MENU_SALES_TITLE = "======== %d년 메뉴별 판매량 ========"
    const val NO_MENU_SALES_DATA = "%d년 메뉴 판매 데이터가 없습니다."
    const val MENU_RANK_FORMAT = "%d위. %s - 판매량:%d개 / 총매출: %s원"
}