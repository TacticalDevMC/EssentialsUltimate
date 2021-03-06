package essentialsapi.utils.essentialsutils;

import nl.tacticaldev.essentials.interfaces.IEssentials;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtil {

    private static final DecimalFormat twoDPlaces = new DecimalFormat("#,###.##");
    private static final DecimalFormat currencyFormat = new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.US));

    // This field is likely to be modified in com.earth2me.essentials.Settings when loading currency format.
    // This ensures that we can supply a constant formatting.
    private static NumberFormat PRETTY_FORMAT = NumberFormat.getInstance(Locale.US);

    static {
        twoDPlaces.setRoundingMode(RoundingMode.HALF_UP);
        currencyFormat.setRoundingMode(RoundingMode.FLOOR);

        PRETTY_FORMAT.setRoundingMode(RoundingMode.FLOOR);
        PRETTY_FORMAT.setGroupingUsed(true);
        PRETTY_FORMAT.setMinimumFractionDigits(2);
        PRETTY_FORMAT.setMaximumFractionDigits(2);
    }

    // this method should only be called by Essentials
    public static void internalSetPrettyFormat(NumberFormat prettyFormat) {
        PRETTY_FORMAT = prettyFormat;
    }

    public static String shortCurrency(final BigDecimal value, final IEssentials ess) {
        if (ess.getSettings().isCurrencySymbolSuffixed()) {
            return formatAsCurrency(value) + ess.getSettings().getCurrencySymbol();
        }
        return ess.getSettings().getCurrencySymbol() + formatAsCurrency(value);
    }

    public static String formatDouble(final double value) {
        return twoDPlaces.format(value);
    }

    public static String formatAsCurrency(final BigDecimal value) {
        String str = currencyFormat.format(value);
        if (str.endsWith(".00")) {
            str = str.substring(0, str.length() - 3);
        }
        return str;
    }

    public static String formatAsPrettyCurrency(BigDecimal value) {
        String str = PRETTY_FORMAT.format(value);
        if (str.endsWith(".00")) {
            str = str.substring(0, str.length() - 3);
        }
        return str;
    }

    public static String displayCurrency(final BigDecimal value, final IEssentials ess) {
        String currency = formatAsPrettyCurrency(value);
        String sign = "";
        if (value.signum() < 0) {
            currency = currency.substring(1);
            sign = "-";
        }
        if (ess.getSettings().isCurrencySymbolSuffixed()) {
            return sign + currency + ess.getSettings().getCurrencySymbol();
        }
        return sign + ess.getSettings().getCurrencySymbol() + currency;
    }

    public static String displayCurrencyExactly(final BigDecimal value, final IEssentials ess) {
        String currency = value.toPlainString();
        String sign = "";
        if (value.signum() < 0) {
            currency = currency.substring(1);
            sign = "-";
        }
        if (ess.getSettings().isCurrencySymbolSuffixed()) {
            return sign + currency + ess.getSettings().getCurrencySymbol();
        }
        return sign + ess.getSettings().getCurrencySymbol() + currency;
    }

    public static String sanitizeCurrencyString(final String input, final IEssentials ess) {
        String symbol = ess.getSettings().getCurrencySymbol();
        boolean suffix = ess.getSettings().isCurrencySymbolSuffixed();
        if (input.contains(symbol)) {
            return suffix ? input.substring(0, input.indexOf(symbol)) : input.substring(symbol.length());
        }
        return input;
    }

    public static boolean isInt(final String sInt) {
        try {
            Integer.parseInt(sInt);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}