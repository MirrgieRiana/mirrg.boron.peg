package mirrg.boron.peg.ilmenite.objects;

import java.util.Optional;
import java.util.function.Consumer;

public final class ObjectNumber extends ObjectScalar
{

	public final double value;
	public final Optional<String> oExpression;

	public ObjectNumber(double value)
	{
		this.value = value;
		this.oExpression = Optional.empty();
	}

	public ObjectNumber(double value, String expression)
	{
		this.value = value;
		this.oExpression = Optional.of(expression);
	}

	@Override
	public void stringify(Consumer<String> stringBuilder)
	{
		stringBuilder.accept(oExpression.isPresent() ? oExpression.get() : "" + value);
	}

}
