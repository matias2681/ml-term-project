awk '{if(match($0,"NaN")==0) {print $0}}' features.csv > features-no-nan.csv