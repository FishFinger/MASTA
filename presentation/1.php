<div class="slide">
	<h1>GMIN334</h1>
	<div style="text-align:center">
	<h2>Projet Sociétés Virtuelles</h2>
	<p><span>Université Montpellier 2</span></p>
	<p><span style="font-style:italic">Thibaut Marmin - Clément Sipieter</span></p>
	<p>Mardi 20 Novembre 2012</p>
	</div>
</div>

<div class="slide">
	<h1>Plan</h1>
	
	<ol>
		<li><b><u>Problématique</u></b></li>
    <li>Allocation émergente</li>
    <li>Allocation centralisée</li>
    <li>Allocation décentralisée</li>
		<li>Applications</li>
	</ol>
</div>
	
<div class="slide">
	<h1>Problématique</h1>
	<h3>Domaines d'application des SMA</h3>
	<table style="width:95%;height:70%;text-align:center;" title="Énumération des différents types d'agents page 21 : http://www-desir.lip6.fr/~briot/cv/sommaire-ic2-sma01.pdf">
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
		<tr>
			<td colspan="3" style="border-top:2px solid black">
			</td>
		<tr>
			<td><b>Enjeux :</b></td>
			<td style="font-weight:bold;color:green">Autonomie</td>
			<td style="font-weight:bold;color:brown">Organisation collective</td>
		</tr>
	</table>
</div>
<div class="slide">

	<h3>Coopération</h3>

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
			<p style="font-style:italic;margin-top:-1em">Systèmes de répartition des tâches.</p></li>
	</ul>
</div>

<div class="slide">
	<h1>Problématique</h1>
	<div class="incremental">
		<div style="max-width:48%;position:absolute;">
			<h3>Question</h3>
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
			<h3>Besoins pour l'allocation de tâches</h3>
			<ol class="incremental">
				<li>Décomposer une tâche</li>
				<li>Définition de rôles</li>
				<li>Systèmes d'allocation</li>
			</ol>
		</div>
	</div>
</div>

<div class="slide decompo">
	<h1>Problématique</h1>
	<h3>Décomposer une tâche</h3>
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
	<h1>Problématique</h1>
	<h3>Définition des rôles</h3>
	<div class="incremental" 
	 style="margin-left: 4em; position: relative"> 
	  <img src="img/client-fournisseur.png" alt="" 
	   style="position: static; vertical-align: bottom;width:20em"/> 
	  <img src="img/fournisseur-client.png" alt="" 
	    style="position: absolute; left: 0; top: 0;;width:20em" /> 
	  <img src="img/fournisseur-mediateur-client.png" alt="" 
	    style="position: absolute; left: 0; top: 0;width:20em" /> 
	</div> 
</div>
