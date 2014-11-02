
function init(lat)
{
    var parsed = JSON.parse(lat);
    console.log(parsed[3]);
    var loc = new google.maps.LatLng(42.26, -71.7);
    var myOptions = {
        zoom: 8,
        center: loc,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

    for(var i = 0; i < parsed.length; i++){
        var locLoop = new google.maps.LatLng(parsed[i].latitude, parsed[i].longitude);
        var hospitalName = parsed[i].name;
        var markerLoop = createMarker(locLoop, "Hospital number 1", hospitalName, map);
        markerLoop.setMap(map);
    }


    var marker = createMarker(loc, "Hospital number 1", "Lorem Ipsum", map);
    marker.setMap(map);
    return map;
}



//creates a google maps marker out of a location, a title, an image, and text to go in the info box

function createMarker(loc, name, infoText, map)
{
    color = RGBtoString(150, 150, 0);
    console.log(color);
    var marker = new google.maps.Marker({
        title: name,
        icon: {
            //path: 'M 125,5 155,90 245,90 175,145 200,230 125,180 50,230 75,145 5,90 95,90 z',
            path: google.maps.SymbolPath.CIRCLE,
            strokeWeight: 8,

            strokeColor: color,
            fillColor: color,
            scale: 7
        },
        position: loc
    });
    var infowindow = new google.maps.InfoWindow();
    google.maps.event.addListener(marker, 'click', function() {
        infowindow.close();
        infowindow.setContent(infoText);
        infowindow.open(map, this);
    });
    return marker;
}

//a conversion of RGB to an ascii hex representation of a color

function RGBtoString(red, green, blue)
{
    return ("#" +
            add0(red.toString(16)) +
            add0(green.toString(16)) +
            add0(blue.toString(16)));
}

function add0(num){
    return (num.length < 2) ? ("0" + num) : (num)
}



