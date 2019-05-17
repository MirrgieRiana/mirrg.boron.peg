package mirrg.boron.peg.ilmenite;

import static mirrg.boron.peg.UtilsSyntax.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import mirrg.boron.peg.core.ParseResult;
import mirrg.boron.peg.core.Syntax;
import mirrg.boron.peg.ilmenite.formulae.FormulaBase;
import mirrg.boron.peg.ilmenite.formulae.FormulaFunction;
import mirrg.boron.peg.ilmenite.formulae.FormulaLiteral;
import mirrg.boron.peg.ilmenite.formulae.FormulaPack;
import mirrg.boron.peg.ilmenite.formulae.FormulaVariable;
import mirrg.boron.peg.ilmenite.objects.ObjectFunction;
import mirrg.boron.peg.ilmenite.objects.ObjectNumber;
import mirrg.boron.peg.ilmenite.objects.ObjectScalar;
import mirrg.boron.peg.ilmenite.objects.ObjectValue;
import mirrg.boron.peg.ilmenite.objects.ObjectVector;
import mirrg.boron.peg.syntaxes.SyntaxSlot;
import mirrg.boron.util.struct.ImmutableArray;
import mirrg.boron.util.struct.Struct0;
import mirrg.boron.util.struct.Struct2;
import mirrg.boron.util.suppliterator.ISuppliterator;

public class IlmeniteScript
{

	public static void main(String[] args) throws IlmeniteRuntimeException
	{
		use("1+1");
		use("1+1+1");
		use("1+1-50");
		use("10*20");
		use("10*20/2");
		use("3*9+8/2-6*9+14/7");
		use("3*(9+8)/2-(6*9+14)/7");
		use("3.14E-2*2");
		use("pi*10");
		use("_binaryPlus");
		use("1,2,3");
		use("5+8");
		use("()+(5,8)");
		use("_binaryPlus(5,8)");
	}

	private static void use(String src) throws IlmeniteRuntimeException
	{
		IIlmeniteFormula formula = new IlmeniteScript().parse(src);
		System.out.println(formula);
		System.out.println(formula.eval());
	}

	private static Context getRootContext()
	{
		Context context = new Context();

		context.stackFrame.declareVariable("pi").value = new ObjectVector(new ObjectNumber(Math.PI));
		if (identifier.equals("_binaryPlus")) return new ObjectFunction() {
			@Override
			public ObjectValue apply(ObjectVector arguments) throws IlmeniteRuntimeException
			{
				if (arguments.array.length() != 2) throw new IlmeniteRuntimeException("Illegal arguments length: " + arguments.array.length() + " != " + 2);
				ObjectNumber a = IlmeniteScript.cast(ObjectNumber.class, arguments.array.get(0));
				ObjectNumber b = IlmeniteScript.cast(ObjectNumber.class, arguments.array.get(1));
				return new ObjectNumber(a.value + b.value);
			}
		};
		if (identifier.equals("_binaryMinus")) return new ObjectFunction() {
			@Override
			public ObjectValue apply(ObjectVector arguments) throws IlmeniteRuntimeException
			{
				if (arguments.array.length() != 2) throw new IlmeniteRuntimeException("Illegal arguments length: " + arguments.array.length() + " != " + 2);
				ObjectNumber a = IlmeniteScript.cast(ObjectNumber.class, arguments.array.get(0));
				ObjectNumber b = IlmeniteScript.cast(ObjectNumber.class, arguments.array.get(1));
				return new ObjectNumber(a.value - b.value);
			}
		};
		if (identifier.equals("_binaryAsterisk")) return new ObjectFunction() {
			@Override
			public ObjectValue apply(ObjectVector arguments) throws IlmeniteRuntimeException
			{
				if (arguments.array.length() != 2) throw new IlmeniteRuntimeException("Illegal arguments length: " + arguments.array.length() + " != " + 2);
				ObjectNumber a = IlmeniteScript.cast(ObjectNumber.class, arguments.array.get(0));
				ObjectNumber b = IlmeniteScript.cast(ObjectNumber.class, arguments.array.get(1));
				return new ObjectNumber(a.value * b.value);
			}
		};
		if (identifier.equals("_binarySlash")) return new ObjectFunction() {
			@Override
			public ObjectValue apply(ObjectVector arguments) throws IlmeniteRuntimeException
			{
				if (arguments.array.length() != 2) throw new IlmeniteRuntimeException("Illegal arguments length: " + arguments.array.length() + " != " + 2);
				ObjectNumber a = IlmeniteScript.cast(ObjectNumber.class, arguments.array.get(0));
				ObjectNumber b = IlmeniteScript.cast(ObjectNumber.class, arguments.array.get(1));
				return new ObjectNumber(a.value / b.value);
			}
		};
		if (identifier.equals("_arrayComma")) return new ObjectFunction() {
			@Override
			public ObjectValue apply(ObjectVector arguments) throws IlmeniteRuntimeException
			{
				return arguments;
			}
		};
		if (identifier.equals("_rightBracketsRound")) return new ObjectFunction() {
			@Override
			public ObjectValue apply(ObjectVector arguments) throws IlmeniteRuntimeException
			{

				return arguments;
			}
		};
	}

	private IIlmeniteFormula parse(String src)
	{
		SyntaxSlot<IIlmeniteFormula> syntaxFormula = slot();

		Syntax<IIlmeniteFormula> syntaxFloatExponential = pack(regex("[0-9]+\\.[0-9]+[eE][+-]?[0-9]+"),
			s -> new FormulaLiteral(new ObjectNumber(Double.parseDouble(s), s)));
		Syntax<IIlmeniteFormula> syntaxFloat = pack(regex("[0-9]+\\.[0-9]+"),
			s -> new FormulaLiteral(new ObjectNumber(Double.parseDouble(s), s)));
		Syntax<IIlmeniteFormula> syntaxIntegerExponential = pack(regex("[0-9]+[eE][+-]?[0-9]+"),
			s -> new FormulaLiteral(new ObjectNumber(Double.parseDouble(s), s)));
		Syntax<IIlmeniteFormula> syntaxInteger = pack(regex("[0-9]+"),
			s -> new FormulaLiteral(new ObjectNumber(Double.parseDouble(s), s)));
		Syntax<IIlmeniteFormula> syntaxVariable = pack(regex("[a-zA-Z_][a-zA-Z0-9_]*"),
			s -> new FormulaVariable(s));
		Syntax<IIlmeniteFormula> syntaxBrackets = or((IIlmeniteFormula) null)
			.or(pack(tunnel((IIlmeniteFormula) null)
				.and(string("("))
				.extract(syntaxFormula)
				.and(string(")")),
				s -> s.get()))
			.or(pack(serial(Struct0::new)
				.and(string("("))
				.and(string(")")),
				s -> new FormulaLiteral(ObjectVector.EMPTY)));

		Syntax<IIlmeniteFormula> syntaxFactor = or((IIlmeniteFormula) null)
			.or(syntaxFloatExponential)
			.or(syntaxFloat)
			.or(syntaxIntegerExponential)
			.or(syntaxInteger)
			.or(syntaxVariable)
			.or(syntaxBrackets);

		Syntax<IIlmeniteFormula> syntaxAfter = pack(serial(Struct2<IIlmeniteFormula, List<IIlmeniteFormula>>::new)
			.and(syntaxFactor, Struct2::setX)
			.and(repeat(or((IIlmeniteFormula) null)
				.or(pack(syntaxBrackets,
					f -> {
						return new FormulaBase() {
							@Override
							public ObjectValue eval(Context context) throws IlmeniteRuntimeException
							{
								return new ObjectFunction() {
									@Override
									public ObjectValue apply(ObjectVector arguments) throws IlmeniteRuntimeException
									{
										if (arguments.array.length() != 1) throw new IlmeniteRuntimeException("Illegal arguments length: " + arguments.array.length() + " != " + 1);
										ObjectFunction function = IlmeniteScript.cast(ObjectFunction.class, arguments.array.get(0));
										ObjectValue right = f.eval(context);
										return function.apply(ObjectVector.pack(right));
									}
								};
							}

							@Override
							public void stringify(Consumer<String> stringBuilder)
							{
								stringBuilder.accept("CALL_FUNCTION[");
								f.stringify(stringBuilder);
								stringBuilder.accept("]");
							}
						};
					}))), Struct2::setY),
			t -> {
				IIlmeniteFormula head = t.x;
				for (IIlmeniteFormula tail : t.y) {
					head = new FormulaFunction(tail, head);
				}
				return head;
			});

		Syntax<IIlmeniteFormula> syntaxMul = pack(serial(Struct2<IIlmeniteFormula, List<Struct2<IIlmeniteFormula, IIlmeniteFormula>>>::new)
			.and(syntaxAfter, Struct2::setX)
			.and(repeat(serial(Struct2<IIlmeniteFormula, IIlmeniteFormula>::new)
				.and(or((IIlmeniteFormula) null)
					.or(pack(string("*"), s -> new FormulaVariable("_binaryAsterisk")))
					.or(pack(string("/"), s -> new FormulaVariable("_binarySlash"))), Struct2::setX)
				.and(syntaxAfter, Struct2::setY)), Struct2::setY),
			t -> {
				IIlmeniteFormula head = t.x;
				for (Struct2<IIlmeniteFormula, IIlmeniteFormula> tail : t.y) {
					head = new FormulaFunction(tail.x, new FormulaPack(head, tail.y));
				}
				return head;
			});

		Syntax<IIlmeniteFormula> syntaxAdd = pack(serial(Struct2<IIlmeniteFormula, List<Struct2<IIlmeniteFormula, IIlmeniteFormula>>>::new)
			.and(syntaxMul, Struct2::setX)
			.and(repeat(serial(Struct2<IIlmeniteFormula, IIlmeniteFormula>::new)
				.and(or((IIlmeniteFormula) null)
					.or(pack(string("+"), s -> new FormulaVariable("_binaryPlus")))
					.or(pack(string("-"), s -> new FormulaVariable("_binaryMinus"))), Struct2::setX)
				.and(syntaxMul, Struct2::setY)), Struct2::setY),
			t -> {
				IIlmeniteFormula head = t.x;
				for (Struct2<IIlmeniteFormula, IIlmeniteFormula> tail : t.y) {
					head = new FormulaFunction(tail.x, new FormulaPack(head, tail.y));
				}
				return head;
			});

		Syntax<IIlmeniteFormula> syntaxComma = pack(serial(Struct2<IIlmeniteFormula, List<IIlmeniteFormula>>::new)
			.and(syntaxAdd, Struct2::setX)
			.and(repeat(pack(tunnel((IIlmeniteFormula) null)
				.and(string(","))
				.extract(syntaxAdd),
				s -> s.get())), Struct2::setY)
			.and(optional(string(","))),
			t -> {
				if (t.y.size() == 0) {
					return t.x;
				} else {
					return new FormulaFunction(new FormulaVariable("_arrayComma"), ISuppliterator.concat(
						ISuppliterator.of(t.x),
						ISuppliterator.ofIterable(t.y))
						.toArray(IIlmeniteFormula[]::new));
				}
			});

		syntaxFormula.setSyntax(syntaxComma);

		ParseResult<IIlmeniteFormula> result = syntaxFormula.matches(src);

		return result.isValid ? result.node.value : null;
	}

	public static ObjectVector vector(Iterable<ObjectValue> valuesIn)
	{
		List<ObjectScalar> scalarsOut = new ArrayList<>();
		for (ObjectValue valueIn : valuesIn) {
			if (valueIn instanceof ObjectVector) {
				for (ObjectScalar scalarIn : ((ObjectVector) valueIn).array) {
					scalarsOut.add(scalarIn);
				}
			} else if (valueIn instanceof ObjectScalar) {
				scalarsOut.add((ObjectScalar) valueIn);
			} else {
				throw new AssertionError();
			}
		}
		return new ObjectVector(ImmutableArray.ofList(scalarsOut));
	}

	public static <T> T cast(Class<T> clazz, ObjectValue value) throws IlmeniteRuntimeException
	{
		if (clazz.isInstance(value)) {
			return clazz.cast(value);
		} else {
			throw new IlmeniteRuntimeException("Cast error: " + value + " -> " + clazz.getSimpleName());
		}
	}

}
