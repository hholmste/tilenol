package com.instantviking.tilenol;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
{
    UninitializedCenterTest.class, UnitializedNeighboursTest.class,
    NorthernConnectionsTest.class })
public class FittingTests
{

}
