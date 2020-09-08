package com.example;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({DeserializationTests.class, FilteringFunctionsTests.class, AnalysisFunctionsTests.class})
public class JunitTestSuite {}
