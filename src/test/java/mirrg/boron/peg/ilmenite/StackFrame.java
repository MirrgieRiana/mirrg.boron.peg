package mirrg.boron.peg.ilmenite;

import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

public class StackFrame
{

	private final Optional<StackFrame> oParent;
	private Map<String, Variable> table = new Hashtable<>();

	public StackFrame()
	{
		this.oParent = Optional.empty();
	}

	public StackFrame(StackFrame parent)
	{
		this.oParent = Optional.of(parent);
	}

	public Variable declareVariable(String name) throws DuplicateException
	{
		if (table.containsKey(name)) {
			throw new DuplicateException("Duplicated variable: " + name);
		} else {
			Variable variable = new Variable();
			table.put(name, variable);
			return variable;
		}
	}

	public Optional<Variable> getVariable(String name)
	{
		Variable variable = table.get(name);
		if (variable != null) {
			return Optional.of(variable);
		} else {
			if (oParent.isPresent()) {
				return oParent.get().getVariable(name);
			} else {
				return Optional.empty();
			}
		}
	}

}
