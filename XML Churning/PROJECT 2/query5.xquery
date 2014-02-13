xquery version "1.0";
<studio_movie_desc>
 {
 for $s in distinct-values(doc("dvdtitles.xml")//record/Studio)
 return
     <studio_ds>
   <studio_name>{data($s)}</studio_name>
   {
   for $d in (doc("dvdtitles.xml")//record[Studio=$s]/DVDTitle)
   return
   <dvd_name>
     {data($d)}
   {
   for $t in (doc("reviews.xml")//REVIEWS[dvdtitle=$d])
     return
		<dvd_review>
   	 	{data($t/description)}
		</dvd_review>
}
</dvd_name>
}
	</studio_ds>
}	
</studio_movie_desc>