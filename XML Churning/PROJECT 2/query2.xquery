xquery version "1.0";
<Studios>{
  for $a in distinct-values(doc("dvdtitles.xml")//record/Studio)
  order by $a
  return
<Studio>
<studio_name>{data($a)}</studio_name>  
{
  for $b in(doc("dvdtitles.xml")//record[Studio=$a]/DVDTitle)
   order by $b
     return 
     <dvd_title>{data($b)}</dvd_title>
   	}
	</Studio>
	}
</Studios>