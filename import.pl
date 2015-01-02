#!/usr/bin/perl

$dir = "./import";

@resolutions = ("1", "5", "10", "15", "30", "60");

foreach $resolution (@resolutions){
	print "Have resolution: $resolution";
}

print "*** READING 1 MINUTE BARS ***\n";

$resolution = 1;
opendir($dh, $dir);
@files = grep { /^*_$resolution.csv$/ } readdir($dh);
close($dh);

foreach $file (@files){
   print "File: $file\n";
}

#foreach $file (@files){
#  print "Importing: $file\n";
#  system(qq~ mysql -pSSmxynk:: -u root autoStock -e "load data local infile '$dir/$file' into table stockHistoricalPrices fields terminated by ',' lines terminated by '\\n' ignore 1 lines (symbol, \@test, priceOpen, priceHigh, priceLow, priceClose, sizeVolume) set dateTime = str_to_date(\@test, '\%d-\%b-\%Y \%k:\%i');" ~);
#}