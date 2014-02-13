xquery version "1.0";
<title_review>
 {
  for $a in distinct-values(doc("reviews.xml")//REVIEWS/REVIEWER)
    order by $a ascending
    return 
	<reviewer>
	<name>{$a}</name>
	{
	for $b in (doc("reviews.xml")//REVIEWS[REVIEWER=$a]/DVDTITLE)
	return
  <dvd>
     {data($b)}
    	</dvd>
}
</reviewer>
}
</title_review>