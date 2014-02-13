xquery version "1.0";
<avg_ratings>
 {
  for $p in distinct-values(doc("reviews.xml")//REVIEWS/DVDTITLE)
 let $a := avg(doc("reviews.xml")//REVIEWS[DVDTITLE=$p]/RATING)
 order by $a descending
 return 
  <dvd_Rating_avg>
    <title_name>{$p}</title_name>
    <avg> {$a} </avg>
  </dvd_Rating_avg>
}
</avg_ratings>