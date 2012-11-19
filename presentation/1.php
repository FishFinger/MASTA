<div class="slide">
	<h1>GMIN334 - Sociétés virtuelles</h1>
	<div style="text-align:center;border:2px solid silver;margin-top:5em;background-color:#F0F0F0;border-left:none;border-right:none">
	<h2 style="border:none;">Allocations de tâches dans les SMA</h2>
	<p><span>Université Montpellier 2</span></p>
	<p><span style="font-style:italic">Thibaut Marmin - Clément Sipieter</span></p>
	<p>Mardi 20 Novembre 2012</p>
	</div>
</div>

<div class="slide">
	<h1>Plan</h1>
		<div class="vbox"></div>
	<div class="hbox" style="height: auto;">
	<ul>
		<li><b><u>Problématique</u></b></li>
    <li>Allocation émergente</li>
    <li>Allocation centralisée</li>
    <li>Allocation décentralisée</li>
    <li>Projet</li>
	</ul>
	</div>
</div>
	
<div class="slide">
	<h1>Problématique - Contexte</h1>
	<h2>Domaines d'application des SMA</h2>
  	<div class="incremental"  style="height:80%">
	<div  style="height:100%">
	<table style="width:90%;height:100%;text-align:center;" title="Énumération des différents types d'agents page 21 : http://www-desir.lip6.fr/~briot/cv/sommaire-ic2-sma01.pdf">
		<tr>
			<td>Agent cognitif</td>
			<td title="Par exemple : démons Unix">Agent logiciel</td>
			<td>Agent mobile</td>
		</tr>
		<tr>
			<td>Agent assistant</td>
			<td>Agent robotique</td>
			<td>Résolution distribuée de problèmes</td>
		</tr>
		<tr>
			<td>Agent réactif</td>
			<td>Vie artificielle</td>
			<td>Simulation multi-agent</td>
		</tr>
		<tr>
			<td></td>
			<td>Agent de loisir</td>
			<td></td>
		</tr>
	</table>
	</div>
	<div class="popup spopup">
			<h2>Deux caractéristiques</h2>
			<p style="font-weight:bold;color:green">Autonomie</p>
			<p style="font-weight:bold;color:orange">Organisation collective</p>
        </div>
	</div>
</div>
<div class="slide">
	<h1>Problématique - Contexte</h1>

	<h2>Coopération</h2>

<!--
Bouquin pas ferber
	<ul>
		<li title="1.3">Coordination
			<p>Allocation / Partage de ressources rares dans le but d'atteindre un but commun.</p>
			<p style="font-style:italic">Prise de décision distribuée</p></li>
		<li title="1.4">Négociation
			<p>Résolution de conflits grâce à l'échange d'informations.</p></li>
			<p style="font-style:italic"></p></li>
		<li title="1.5">Planification
			<p>Division du travail dans un ordre précis afin d'orienter les agents vers un but commun.</p></li>
		<li title="1.6">Communication
			<p>Communication directe entre agents / Communication via un blackboard.</p></li>
	</ul>
-->
	<ul title="ferber 2.5">
		<li title="p. 400">Résolution de conflits
			<p>Éviter les désaccords entre agents.</p>
			<p style="font-style:italic;margin-top:-1em">Arbitrage / Négociation.</p></li>
		<li title="p. 400">Coordination
			<p>Assure la bonne coopération entre les agents autonomes.</p>
			<p style="font-style:italic;margin-top:-1em">Besoin fort de communcations entre agents.</p></li>
		<li title="p. 400">Collaboration
			<p>Permet la réalisation d'une unique tâche par plusieurs agents.</p>
			<p style="font-style:italic;margin-top:-1em">Système de répartition des tâches.</p></li>
		</li>
	</ul>
</div>

<div class="slide">
	<h1>Problématique - Définition</h1>
	<div class="incremental">
		<div style="max-width:48%;position:absolute;">
			<h2>Question <span class="bib">[FERBER 1995]</span></h2>
			<ul class="incremental">
				<li>Qui doit faire quoi ?</li>
				<li>et avec quels moyens ?</li>
				<li>en fonction
					<ul>
						<li>des buts des agents,</li>
						<li>des compétences des agents,</li>
						<li>et des contraintes contextuelles</li>
					</ul>
				</li>
			</ul>
		</div>
		<div style="max-width:48%;float:right">
			<h2>Besoins pour l'allocation de tâches</h2>
			<ol class="incremental">
				<li>Décomposer une tâche</li>
				<li>Définition de rôles</li>
				<li>Système d'allocation</li>
			</ol>
		</div>
	</div>
</div>

<div class="slide decompo">
	<h1>Problématique - Besoin n°1</h1>
	<h2>Décomposer une tâche <span class="bib">[Bond & Gasser 1988]</span> <span class="bib">[Gasser & Hill 1990]</span></h2>
	<p><b>Contrainte</b> : avoir des tâches indépendantes</p>
	<ul>
		<li>Minimiser la coordination</li>
		<li>Diminuer la quantité d'informations échangée</li>
		<li>Éviter les conflits</li>
		
	</ul>
</div>

<div class="background decompo" style="height:100%;top:2.1em;background:none;">
	<img alt="" src="img/taches_decompo.svg" style="opacity:0.5;position:absolute;bottom:0;right:0;width:100%;height:100%;"/>
</div>

<div class="slide">
	<h1>Problématique - Besoin n°2</h1>
	<h2>Définition des rôles</h2>
	<div class="incremental" 
	 style="margin: auto; position: relative; width:20em;"> 
	  <img src="img/agent-agent.png" alt="" 
	   style="position: static; vertical-align: bottom;width:20em"/> 
	  <img src="img/client-fournisseur.png" alt="" 
	    style="position: absolute; left: 0; top: 0;width:20em" /> 
	  <img src="img/fournisseur-client.png" alt="" 
	    style="position: absolute; left: 0; top: 0;width:20em" /> 
	  <img src="img/fournisseur-mediateur-client.png" alt="" 
	    style="position: absolute; left: 0; top: 0;width:20em" /> 
	</div> 
</div>

<div class="slide">
	<h1>Problématique - Besoin n°3</h1>
	<h2>Système d'allocation</h2>
	<div class="vbox"></div>
	<div class="hbox" style="height:auto;text-align:center;margin-bottom:1em">
		<p style="font-size:1.3em;font-weight:bold">Allocation émergente</p>
	</div>
	<div class="hbox" style="height:auto;text-align:center;margin-bottom:1em;">
		<p style="font-size:1.3em;font-weight:bold">Allocation centralisée</p>
	</div>
	<div class="hbox" style="height:auto;text-align:center;">
		<p style="font-size:1.3em;font-weight:bold">Allocation décentralisée</p>
	</div>
</div>
