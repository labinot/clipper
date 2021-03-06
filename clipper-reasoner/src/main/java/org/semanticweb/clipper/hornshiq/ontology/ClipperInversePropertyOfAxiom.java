package org.semanticweb.clipper.hornshiq.ontology;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClipperInversePropertyOfAxiom implements ClipperTBoxAxiom {
	int role1, role2;

	@Override
	public String toString() {
		String r1 = String.valueOf((role1) / 2 * 2);

		/* inverse role */
		if (role1 % 2 == 1) {
			r1 = "inv(" + r1 + ")";
		}

		String r2 = String.valueOf((role2) / 2 * 2);

		/* inverse role */
		if (role2 % 2 == 1) {
			r2 = "inv(" + r2 + ")";
		}

		return String.format("Inv(%s, %s)", r1, r2);
	}
}
