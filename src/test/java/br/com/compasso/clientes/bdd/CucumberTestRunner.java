package br.com.compasso.clientes.bdd;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, 
	features = "src/test/resources", 
	monochrome = true, dryRun = false)
public class CucumberTestRunner {}
