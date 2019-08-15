var numsquares = 6;
var colors = [];
var pickedcolor;
var squares = document.querySelectorAll(".square");
var colordisplay = document.getElementById("colordisplay");
var messagedisplay = document.querySelector("#message");
var h1 = document.querySelector("h1");
var resetbutton = document.getElementById("reset");
var modebuttons = document.getElementsByClassName("mode");

init();

function init() {
    setUpModeButton();
    setUpSquares();
    reset();
}

function setUpModeButton() {
    for (var i = 0; i < modebuttons.length; i++) {
        modebuttons[i].addEventListener("click", function () {
            modebuttons[0].classList.remove("selected");
            modebuttons[1].classList.remove("selected");
            modebuttons[2].classList.remove("selected");
            this.classList.add("selected");
            numsquares = 6;
            reset();

        });
    }
}

function setUpSquares() {
    for (var i = 0; i < squares.length; i++) {
        squares[i].addEventListener("click", function () {
            var clickedcolor = this.style.backgroundColor;
            changecolors("rgb(27, 6, 210)");
          });
    }
}

function reset() {
    colors = generateRandomColors(numsquares);
    pickedcolor = pickcolor();
    colordisplay.textContent = "Click on any color";
    resetbutton.textContent = "Reset";

    for (var i = 0; i < squares.length; i++) {
        if (colors[i]) {
            squares[i].style.display = "block";
            squares[i].style.backgroundColor = colors[i];
        } else {
            squares[i].style.display = "none";
        }

    }
    h1.style.backgroundColor = "steelblue";
}

resetbutton.addEventListener("click", function () {
    reset();
});

function changecolors(color) {
    for (var i = 0; i < squares.length; i++) {
        squares[i].style.backgroundColor = "rgb(27, 6, 210)";
    }
}

function pickcolor() {
    var random = Math.floor(Math.random() * colors.length);
    return colors[random];
}

function generateRandomColors(num) {
    var arr = [];

    for (var i = 0; i < num; i++) {
        arr.push(randomcolor());
    }
    return arr;
}

function randomcolor() {
    var r = Math.floor(Math.random() * 256);
    var g = Math.floor(Math.random() * 256);
    var b = Math.floor(Math.random() * 256);
    return "rgb(" + r + ', ' + g + ', ' + b + ")"
}