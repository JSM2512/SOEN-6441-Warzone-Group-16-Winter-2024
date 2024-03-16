import Controller.MainGameEngineTest;
import Controller.MapControllerTest;
import Controller.PlayerControllerTest;
import Models.*;
import Utils.CommandHandlerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The type Testsuite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        MainGameEngineTest.class,
        MapControllerTest.class,
        PlayerControllerTest.class,
        AdvanceOrderTest.class,
        CardAirliftTest.class,
        CardBlockadeTest.class,
        CardBombTest.class,
        ContinentTest.class,
        CountryTest.class,
        CurrentStateTest.class,
        DeployTest.class,
        MapTest.class,
        OrdersTest.class,
        OrderExecutionPhaseTest.class,
        PhaseTest.class,
        PlayerTest.class,
        CommandHandlerTest.class
})
public class Testsuite {

}
