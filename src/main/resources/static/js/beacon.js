if (!String.prototype.splitWidth) {
    String.prototype.splitWidth = function(width) {
        var count = Math.ceil(this.length / width);
        var lines = new Array(count);
        var start = 0;
        for ( var i = 0; i < count; i++) {
            lines[i] = this.substr(start, Math.min(this.length - start, width));
            start += width;
        }
        return lines;
    };
}

function BeaconData(version, frequency, time, seed, prev, sig, output, status) {
    this.version = version;
    this.frequency = frequency;
    this.timestamp = time;
    this.seed = seed;
    this.prev = prev;
    this.sig = sig;
    this.output = output;
    this.status = status;

    function lineLimitedString(s) {
        var output = s.splitWidth(64);
        var result = "";
        for ( var i = 0; i < output.length; i++) {
            result += "\n" + output[i];
        }
        return result;
    }

    statusString = [ "Normal", "New Chain", "Gap, chain intact" ];

    this.toHtml = function() {
        var html = "<h2 class=\"beacon\">Beacon Record</h2>";
        html += "<dl class=\"beacon\">";

        // version
        html += "<dt>Version:</dt><dd>" + this.version + "</dd>";

        // frequency
        html += "<dt>Frequency:</dt><dd>" + this.frequency + " seconds</dd>";

        // time
        var date = new Date(this.timestamp * 1000);
        html += "<dt>Time:</dt><dd>" + dateString(date) + " "
            + timeString(date) + " (" + this.timestamp + ")</dd>";

        // seed
        html += "<dt>Seed Value:</dt><dd><pre>" + lineLimitedString(this.seed)
            + "</pre></dd>";

        // previous
        html += "<dt>Previous Output:</dt><dd><pre>"
            + lineLimitedString(this.prev) + "</pre></dd>";

        // previous
        html += "<dt>Signature:</dt><dd><pre>" + lineLimitedString(this.sig)
            + "</pre></dd>";

        // output value
        html += "<dt>Output Value:</dt><dd><pre>"
            + lineLimitedString(this.output) + "</pre></dd>";

        // status code
        html += "<dt>Status:</dt><dd>" + this.status + ": "
            + statusString[this.status] + "</dd>";

        html += "</dl>";

        return html;
    };
}

function dateString(d) {
    var curr_date = "" + d.getDate();
    var curr_month = "" + (d.getMonth() + 1);
    if (curr_date.length == 1) {
        curr_date = "0" + curr_date;
    }
    if (curr_month.length == 1) {
        curr_month = "0" + curr_month;
    }
    return curr_month + "/" + curr_date + "/" + d.getFullYear();
}

function timeString(d) {
    var a_p = "";
    var curr_hour = d.getHours();
    var curr_min = d.getMinutes() + "";
    if (curr_hour < 12) {
        a_p = "am";
    } else {
        a_p = "pm";
    }
    if (curr_hour == 0) {
        curr_hour = 12;
    } else if (curr_hour > 12) {
        curr_hour = curr_hour - 12;
    }
    if (curr_min.length == 1) {
        curr_min = "0" + curr_min;
    }
    return curr_hour + ":" + curr_min + " " + a_p;
}