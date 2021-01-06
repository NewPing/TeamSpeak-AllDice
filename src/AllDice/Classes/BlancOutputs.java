package AllDice.Classes;

public class BlancOutputs {
    public static String blanc_w_Output =
            "--$AUTHOR$--\n" +
                    "Rechnung: $RANDNUMBER$ = $SUM$\nSumme: $SUM$+$ADD$\nErgebnis = $RESULT$";
    public static String blanc_sww_Output =
            "--$AUTHOR$--\n" +
                    "Probewurf W$INPUTNUMBER$\nRechnung: $RANDNUMBERS0$\nSumme: $SUM0$+$ADD$ = $RESULT0$\n$OUTPUT0$\n\n" +
                    "Wildcardwurf: W6\nRechnung: $RANDNUMBERS1$\nSumme: $SUM1$+$ADD$ = $RESULT1$\n$OUTPUT1$";
    public static String blanc_sws_Output =
            "--$AUTHOR$--\n" +
                    "Probewurf W$INPUTNUMBER$\nRechnung: $RANDNUMBERS0$\nSumme: $SUM0$+$ADD$ = $RESULT0$\n$OUTPUT0$";
    public static String blanc_swd_Output =
            "--$AUTHOR$--\n" +
                    "Schadenswurf W$INPUTNUMBER0$\nRechnung: $RANDNUMBERS0$\nSumme: $SUM0$\n\n" +
                    "Schadenswurf W$INPUTNUMBER1$\nRechnung: $RANDNUMBERS1$\nSumme: $SUM1$\n\n" +
                    "Ergebnis: $SUM0$ + $SUM1$ +$ADD$ = $RESULT$";
    public static String blanc_swh_Output =
            "--$AUTHOR$--\n" +
                    "Trefferzonenwurf W6: $RANDNUMBER0$\n" +
                    "Trefferzonenwurf W6: $RANDNUMBER1$\n" +
                    "Ergebnis: $SUM$\n\n" +
                    "Trefferzone: $ZONE0$" +
                    "\nZusatzwurf W6: $RANDNUMBER2$ - $ZONE1$";
    public static String blanc_dsa_Output =
            "--$AUTHOR$--\n" +
                    "Ergebnis: $RANDNUMBER0$, $RANDNUMBER1$, $RANDNUMBER2$ => $RESULT$ = $RESSUM$";
    public static String blanc_cth_Output =
            "--$AUTHOR$--\n" +
                    "Ergebnis: $RANDNUMBER$ / ( $INPUTNUMBERS0$ +$INPUTNUMBERS1$ ) => $RESULT$";
    public static String blanc_dim_Output =
            "--$AUTHOR$--\n" +
                    "Ergebnis: $RANDNUMBER$ +$INPUTNUMBER$ = $RESSUM$\n" +
                    "=> $RESULT$";
    public static String blanc_fate_passive_Output =
            "--$AUTHOR$--\n" +
                    "Fertigkeit ist $SKILL$+$MOD$, passiver Widerstand ist eine $GOAL$.\n\n" +
                    "Wurf: $EMOJIS$ = $RESULT$\n" +
                    "Rechnung: $RESULT$+$SKILL$+$MOD$ = $ABILITY$ ( $ABILITYNAME$ )\n" +
                    "Ergebnis: $OUTCOME$ ( $OUTCOMENAME$ )";
    public static String blanc_fate_Sequence_Output =
            "--$AUTHOR$--\n" +
                    "Fertigkeit ist $SKILL$+$MOD$, vorhergehende $RESISTANCETYPE$-Widerstand ist eine $GOAL$.\n\n" +
                    "Wurf: $EMOJIS$ = $RESULT$\n" +
                    "Rechnung: $RESULT$+$SKILL$+$MOD$ = $ABILITY$ ( $ABILITYNAME$ )\n" +
                    "Ergebnis Angreifer: $OUTCOMEATTACKER$ ( $OUTCOMENAMEATTACKER$ )\n" +
                    "Ergebnis Verteidiger: $OUTCOMEDEFENDER$ ( $OUTCOMENAMEDEFENDER$ )";
    public static String blanc_fate_active_Output =
            "--$AUTHOR$--\n" +
                    "Fertigkeit ist $SKILL$+$MOD$, aktiver Widerstand ist Fertigkeit $SKILLOPPONENT$+$MODOPPONENT$.\n\n" +
                    "Wurf Spieler: $EMOJIS$ = $RESULT$\n" +
                    "Rechnung: $RESULT$+$SKILL$+$MOD$ = $ABILITY$ ( $ABILITYNAME$ )\n" +
                    "Wurf Widerstand: $EMOJISOPPONENT$ = $RESULTOPPONENT$\n" +
                    "Rechnung: $RESULTOPPONENT$+$SKILLOPPONENT$+$MODOPPONENT$ = $ABILITYOPPONENT$ ( $ABILITYNAMEOPPONENT$ )\n" +
                    "Ergebnis: $OUTCOME$ ( $OUTCOMENAME$ )";
    public static String blanc_st_Output =
            "--$AUTHOR$--\n" +
                    "( $dies$ ) Erfolge: $successes$, Fehlschläge: $misses$, Schwierigkeit: $difficulty$, $result$";
    public static String blanc_stc_Output =
            "--$AUTHOR$--\n" +
                    "( $dies$ ) Ergebnis: $sum$, Effekte: $effects$";
}
