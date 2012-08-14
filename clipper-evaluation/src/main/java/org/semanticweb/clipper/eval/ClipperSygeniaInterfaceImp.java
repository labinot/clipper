package org.semanticweb.clipper.eval;

import java.io.File;
import java.util.List;

import lombok.Setter;

import org.oxford.comlab.compass.SystemInterface;
import org.semanticweb.clipper.cqparser.CQParser;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.google.common.collect.ImmutableSet;

public class ClipperSygeniaInterfaceImp implements SystemInterface {

	private QAHornSHIQ qaHornSHIQ;

	@Setter
	private String dataSetRoot;

	@Setter
	private String dlvPath;

	private OWLOntology tbox;

	private String queryFile;

	private OWLOntologyManager manager;

	private OWLOntology abox;

	@Override
	public void initializeSystem() throws Exception {
		if (dlvPath == null) {
			throw new IllegalStateException("please call setDlvPath() before initializeSystem() !");
		}

		qaHornSHIQ = new QAHornSHIQ(dlvPath);
	}

	@Override
	public void shutdown() {

	}

	@Override
	public void loadTestToSystem(String ontologyFile, File aBoxFile) throws Exception {
		manager = OWLManager.createOWLOntologyManager();
		OWLOntology tboxOntology = manager.loadOntologyFromOntologyDocument(new File(ontologyFile));
		this.tbox = tboxOntology;

		qaHornSHIQ.setOntologyName(ontologyFile);

		qaHornSHIQ.addOntology(tboxOntology);

		manager.removeOntology(tbox);

		
		OWLOntology aboxOntology = manager.loadOntologyFromOntologyDocument(aBoxFile);
		this.abox = aboxOntology;

		qaHornSHIQ.addOntology(aboxOntology);
		manager.removeOntology(abox);

		CQParser parser = new CQParser(new File(queryFile), ImmutableSet.of(tbox, aboxOntology));
		CQ cq = parser.parse();
		System.out.println(cq);
		qaHornSHIQ.setQuery(cq);
	}

	@Override
	public void loadQuery(int queryIndex) throws Exception {
		String queryFile = String.format("%s/Queries/Query_%02d.txt", dataSetRoot, queryIndex);
		System.out.println(queryFile);
		this.queryFile = queryFile;
	}

	@Override
	public void loadQuery(String atomSymbol, int symbolType) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public long runLoadedQuery() throws Exception {
		List<List<String>> results = qaHornSHIQ.query();

		return results.size();
	}

	@Override
	public void clearRepository() throws Exception {
//		manager.removeOntology(tbox);
//		manager.removeOntology(abox);
	}

}