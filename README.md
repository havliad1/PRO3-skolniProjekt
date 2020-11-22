# Startovací JavaFX projekt (openjfx.io)

**Maven** šablona projektu s JavaFX (v15) + FXML, Java (v14) pro rychlý start.

## Spuštění projektu

1. naklonování projektu - **fork** nebo **clone**

2. nastavení požadovaných verzí v `pom.xml` v bloku `<properties>...</properties>` (verze Java a JavaFX se mohou lišit):
	a. Java => `java-version`
	b. JavaFX => `javafx-version`
	
3. spuštění aplikace:
`Maven` > `openjfx-starter` > `Plugins` > `javafx:run` 
	- ![](img/maven_plugins_run_maven.jpg)
	nebo
	- ![](img/maven_plugins_run_unnamed.jpg)
	
## Úprava konfigurace: 

1. spuštění aplikace přes pravé kliknutí tlačítka myši na `javafx:run` a "**Run 'Unnamed'**"
	- ![](img/maven_plugins_run_unnamed.jpg)
	
2. otevřít editor konfigurací
	- ![](img/edit_config.jpg) 	
	- ![](img/edit_config_main.jpg)

3. pro zvolení jiné verze Javy pro spouštění aplikace:
	-  záložka "**Runner**"
	- odškrtnutí "**Use project default**"
	- zvolení požadované verze JRE
		- (doporučená verze Javy je alespoň 11+)
	- ![](img/edit_config_runner.jpg) 

## Řešení problémů

1. Aplikace nelze spustit:

    - pokud by aplikace nešla spustit, možné řešení může být ve změně JRE (pokud se aplikace spouští pod verzí 1.8)  => změna JRE na vyšší než 1.8 (viz. **Úprava konfigurace**)


# ![](https://i.pinimg.com/236x/e8/b3/a0/e8b3a0244b14d5563b3868da15bec8f7.jpg =300x)

