package org.semanticweb.clipper.hornshiq.rule;

import lombok.Data;

@Data
public class Variable implements Term {

	int index;
	String name;

	public Variable(int index) {
		this.index = index;
	}

	public Variable(String text) {
		this.name = text;
	}

	@Override
	public boolean isVariable() {
		return true;
	}

	@Override
	public String toString() {

		if (name != null)
			return name;

		return "X" + index;
	}

	

}
