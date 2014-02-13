xquery version "1.0";
<studio_title_avg>
 {
  for $p in distinct-values(doc("reviews.xml")//REVIEWS/DVDTITLE)
 for $t in (doc("dvdtitles.xml")//record[DVDTitle=$p])
 let $a:=avg(doc("reviews.xml")//REVIEWS[DVDTITLE=$p]/RATING)
 where $a gt 3
     return
<dvd>    
      <studio_name>{data($t/Studio)}</studio_name>  
	  <dvd_title_name>{data($p)}</dvd_title_name>
	  <avg_rating>{data($a)}</avg_rating>
	    
		
</dvd>
		}
</studio_title_avg>