package AllDice.Models;

import AllDice.Classes.Implementations.JDictionary;

public class UserConfigs {
    public JDictionary<UserConfig> userConfigs = new JDictionary<>();

    public static class UserConfig{
        public String fateOutcomeHighName;
        public String fateAbilityHighName;
        public String fateAbilityLowName;
    }
}
