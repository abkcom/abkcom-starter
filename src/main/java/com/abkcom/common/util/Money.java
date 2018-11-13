package com.abkcom.common.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Money extends Number implements Comparable<Money>, Serializable
{
	private static final long serialVersionUID = 8937664251860658554L;
	public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
	public static final int SCALE = 2;
	public static final Money ZERO = new Money(BigDecimal.ZERO);
	public static final Money ONE = new Money(BigDecimal.ONE);
	public static final Money TEN = new Money(BigDecimal.TEN);

	private BigDecimal amt;

	/**
	 * Constructs ZERO dollar Money
	 */
	public Money()
	{
		this(BigDecimal.ZERO);
	}

	/**
	 * Constructs Money with provided dollar value
	 *
	 * @param dollarValue
	 */
	public Money(String dollarValue)
	{
		amt = new BigDecimal(dollarValue);
		amt = amt.setScale(SCALE, ROUNDING_MODE);
	}

	/**
	 * Constructs Money with provided dollar value
	 *
	 * @param dollarValue
	 */
	public Money(Number dollarValue)
	{
		this(dollarValue.toString());
	}

	/**
	 * Constructs Money with provided dollar value
	 *
	 * @param dollarValue
	 */
	public Money(double dollarValue)
	{
		this(String.valueOf(dollarValue));
	}

	/**
	 * Constructs Money with provided cent value
	 *
	 * @param centValue
	 */
	public Money(int centValue)
	{
		this(new BigInteger(String.valueOf(centValue)));
	}

	/**
	 * Constructs Money with provided cent value
	 *
	 * @param centValue
	 */
	public Money(BigInteger centValue)
	{
		amt = new BigDecimal(centValue, SCALE);
	}

	private Money(BigDecimal value)
	{
		amt = value.setScale(SCALE, ROUNDING_MODE);
	}

	/**
	 * Returns a Money whose value is (this + amount).
	 *
	 * @param amount
	 *          - value to be added to this Money.
	 * @return this + amount
	 */
	public Money add(Money amount)
	{
		return new Money(amt.add(amount.bigDecimalValue()));
	}

	/**
	 * Returns a Money whose value is (this - amount).
	 *
	 * @param amount
	 *          - value to be subtracted from this Money.
	 * @return this - amount
	 */
	public Money sub(Money amount)
	{
		return new Money(amt.subtract(amount.bigDecimalValue()));
	}

	/**
	 * Returns a Money whose value is (this / n). Example: 5/2=2.50; 5/0.1=50; 7/3
	 * = 2.33"
	 *
	 * @param n
	 * @return this / n
	 */
	public Money div(Number n)
	{
		return div(n, ROUNDING_MODE);
	}

	public Money div(Number n, RoundingMode roundingMode)
	{
		BigDecimal number = (n instanceof BigDecimal) ? (BigDecimal) n : new BigDecimal(n.toString());
		return new Money(amt.divide(number, SCALE, roundingMode));
	}

	public BigDecimal div(Money amount, int scale, RoundingMode roundingMode)
	{
		return new BigDecimal(amt.divide(amount.bigDecimalValue(), scale, roundingMode).toString());
	}

	/**
	 * Calculates average. Returns two-element array containing average and
	 * remainder.
	 *
	 * @return average of 7.00 by 3 returns [2.33,0.01]
	 */
	public Money[] avg(Number n)
	{
		Money remainder = remainder(n);
		Money avg = div(n);
		Money[] arr = { avg, remainder };
		return arr;
	}

	/**
	 * Calculates installments based on number of occurrences 'n'
	 * 
	 * @param n
	 *          3 occurrences for amount of 100 will return [33.33, 33.33, 33.34]
	 * @return An array of installment amounts. The last amount is adjusted to
	 *         compensate the difference. 3 occurrences for amount 100 will return
	 *         [33.33, 33.33, 33.34]
	 */
	public Money[] installments(int n)
	{
		if (this.isZero() || n == 0)
		{
			return new Money[0];
		}
		Money[] installments = new Money[n];
		Money[] avg = avg(n);
		for (int i = 0; i < installments.length; i++)
		{
			installments[i] = new Money(avg[0]);
		}
		installments[n - 1] = installments[n - 1].add(avg[1]);
		return installments;
	}

	/**
	 * Calculates installments based on installment amount
	 * 
	 * @param installmentAmount
	 *          - installment amount
	 * @return An array of installment amounts. The last amount is adjusted to
	 *         compensate the difference. Installment amount of 30 for amount of
	 *         100 will return [30.00, 30.00, 30.00, 10.00]
	 */
	public Money[] installments(Money installmentAmount)
	{
		if (this.isZero() || installmentAmount.isZero())
		{
			return new Money[0];
		}
		int n = div(installmentAmount, 0, RoundingMode.UP).intValue();
		Money[] installments = new Money[n];
		Money sum = Money.ZERO;
		for (int i = 0; i < installments.length; i++)
		{
			installments[i] = new Money(installmentAmount);
			sum = sum.add(installments[i]);
		}
		installments[n - 1] = installments[n - 1].sub(sum.sub(this));
		return installments;
	}

	/**
	 * Returns a Money whose value is (this * n). Example: 5*5=25.00; 5*0.1=0.50
	 *
	 * @param n
	 * @return this * n
	 */
	public Money multiply(Number n)
	{
		BigDecimal number = (n instanceof BigDecimal) ? (BigDecimal) n : new BigDecimal(n.toString());
		return new Money(amt.multiply(number));
	}

	/**
	 * Returns a Money whose value is absolute. Example: 0 -> 0.00; -0 -> 0.00; -5
	 * -> 5.00; 1.00 -> 1.00
	 *
	 * @return this < 0 ? -this : this;
	 */
	public Money abs()
	{
		return new Money(amt.abs());
	}

	/**
	 * Returns a Money whose value is (this % n). Example: 875.00%12=-0.04;
	 * 5%3=-0.01; 7%3=0.01;
	 *
	 * @param n
	 * @return this % n
	 */
	public Money remainder(Number n)
	{
		return sub(div(n).multiply(n));
	}

	public Money negate()
	{
		return new Money(amt.negate());
	}

	/**
	 * Determines whether this amount is a negative amount.
	 *
	 * @return <code>true</code>, if this amount is negative (amount < 0);
	 *         <code>false</code>, otherwise.
	 */
	public boolean isNegative()
	{
		return this.lt(ZERO);
	}

	public boolean isNegativeOrZero()
	{
		return this.le(ZERO);
	}

	/**
	 * Determines whether this amount is a positive amount.
	 *
	 * @return <code>true</code>, if this amount is positive (amount > 0);
	 *         <code>false</code>, otherwise.
	 */
	public boolean isPositive()
	{
		return this.gt(ZERO);
	}

	public boolean isPositiveOrZero()
	{
		return this.ge(ZERO);
	}

	/**
	 * Determines whether this amount is a zero amount.
	 *
	 * @return <code>true</code>, if this amount is zero (amount = 0);
	 *         <code>false</code>, otherwise.
	 */
	public boolean isZero()
	{
		return this.equals(ZERO);
	}

	/**
	 * Returns true if this value is less then amount.
	 *
	 * @return <code>true</code>, if (this < amount); <code>false</code>,
	 *         otherwise.
	 */
	public boolean lt(Money amount)
	{
		return this.compareTo(amount) < 0;
	}

	/**
	 * Returns true if this value is less then or equal to amount.
	 *
	 * @return <code>true</code>, if (this <= amount); <code>false</code>,
	 *         otherwise.
	 */
	public boolean le(Money amount)
	{
		return this.compareTo(amount) <= 0;
	}

	/**
	 * Returns true if this value is greater then amount.
	 *
	 * @return <code>true</code>, if (this > amount); <code>false</code>,
	 *         otherwise.
	 */
	public boolean gt(Money amount)
	{
		return this.compareTo(amount) > 0;
	}

	/**
	 * Returns true if this value is greater then or equal to amount.
	 *
	 * @return <code>true</code>, if (this >= amount); <code>false</code>,
	 *         otherwise.
	 */
	public boolean ge(Money amount)
	{
		return this.compareTo(amount) >= 0;
	}

	/**
	 * Returns true if this value is equal to amount.
	 *
	 * @return <code>true</code>, if (this = amount); <code>false</code>,
	 *         otherwise.
	 */
	public boolean eq(Money amount)
	{
		return this.compareTo(amount) == 0;
	}

	public boolean ne(Money amount)
	{
		return this.compareTo(amount) != 0;
	}

	/**
	 * Returns a two-element array containing dollar part of this amount followed
	 * by cent part.
	 *
	 * @return this = this.split()[0] + this.split()[1]
	 */
	public Money[] split()
	{
		Money absAmount = this.abs();
		Money dollars = new Money(absAmount.bigDecimalValue().toBigInteger().toString());
		Money cents = absAmount.sub(dollars);
		// check if original is negative
		if (this.isNegative())
		{
			dollars = dollars.negate();
			cents = cents.negate();
		}
		Money[] arr = { dollars, cents };
		return arr;
	}

	/**
	 * Returns the absolute value of cents in this amount
	 *
	 * @return 12 from -35.12 or 35.12
	 */
	public int getCentsInAmount()
	{
		return (int) split()[1].abs().centValue();
	}

	/**
	 * @return Returns BigDecimal representation of this.
	 */
	public BigDecimal bigDecimalValue()
	{
		return amt;
	}

	/**
	 * @return Returns cents representation of this.
	 */
	public long centValue()
	{
		return amt.unscaledValue().longValue();
	}

	@Override
	public int compareTo(Money amount)
	{
		return amt.compareTo(amount.bigDecimalValue());
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Money))
		{
			return false;
		}
		Money money = (Money) o;
		return amt.equals(money.bigDecimalValue());
	}

	public String format(Currency currency, Locale locale)
	{
		NumberFormat format = NumberFormat.getCurrencyInstance(locale);
		format.setCurrency(currency);
		return format.format(amt);
	}

	public String format(Currency currency, Locale locale, boolean isMinusSignNegativePattern)
	{
		String formatted = format(currency, locale);
		return isMinusSignNegativePattern ? formatted.replace("(", "-").replace(")", "") : formatted;
	}

	public String format(String format)
	{
		DecimalFormat decimalFormat = new DecimalFormat(format);
		return decimalFormat.format(amt);
	}

	public static String toString(Money amount, String defaultValue)
	{
		return amount == null ? defaultValue : amount.toString();
	}

	public static boolean isValid(String amount)
	{
		try
		{
			BigDecimal bd = new BigDecimal(amount);
			return bd.scale() <= SCALE;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * @return String representation of this Money.
	 */
	@Override
	public String toString()
	{
		return amt.toString();
	}

	@Override
	public double doubleValue()
	{
		return amt.doubleValue();
	}

	@Override
	public float floatValue()
	{
		return amt.floatValue();
	}

	@Override
	public int intValue()
	{
		return amt.intValue();
	}

	@Override
	public long longValue()
	{
		return amt.longValue();
	}

	/**
	 * Convert String to Money
	 * 
	 * @param amount
	 * @return Money or NULL if parameter is null or empty.
	 */
	public static Money toMoney(String amount)
	{
		return amount == null || amount.trim().isEmpty() ? null : new Money(amount);
	}

	/**
	 * Convert Object to Money
	 * 
	 * @param amount
	 * @return Money or NULL if parameter is null or empty.
	 */
	public static Money toMoney(Object amount)
	{
		return amount == null ? null : toMoney(amount.toString());
	}

	/**
	 * Convert String to Money
	 * 
	 * @param amount
	 * @return Money or Money.ZERO if parameter is null or empty.
	 */
	public static Money toMoneyOrZero(String amount)
	{
		return amount == null || amount.trim().isEmpty() ? Money.ZERO : new Money(amount);
	}

}
