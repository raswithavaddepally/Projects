xquery version "1.0";
<director_title>
 {
  for $a in distinct-values(doc("dvdtitles.xml")//record/Director)
    return
  <Director>
    <director_name>{data($a)}</director_name>{
     for $b in(doc("dvdtitles.xml")//record[Director=$a]/DVDTitle)
   return 
    <dvd>{data($b)}</dvd> }
</Director>
	}
</director_title>