package entities


final case class TestReportRO(empty: Boolean, failCount: Int, passCount: Int, skipCount: Int, suites: List[TestCasesRO]) 
