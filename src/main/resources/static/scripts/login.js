$("#password").keyup(() => validatePassword());

const colors = ['darkred', 'orangered', 'orange', 'yellowgreen'];
const statuses = ['Poor', 'Weak', 'Average', 'Good'];

const strengthChecks = [
    { test: (p) => /[a-z]+/.test(p), message: 'Add lowercase letters' },
    { test: (p) => /[A-Z]+/.test(p), message: 'Add uppercase letters' },
    { test: (p) => /[0-9]+/.test(p), message: 'Add numbers' },
    { test: (p) => /[$-/:-?{-~!"^_@`\[\]]/.test(p), message: 'Add special characters (e.g. !@#$)' },
];

function validatePassword() {
    const passwordField = $("#password");
    const password = passwordField.val();
    resetStrengthValidation();

    if (password) {
        if (validateLength(password)) {
            passwordField.removeClass("is-invalid").addClass("is-valid");
            validateStrength(password);
        }
    } else {
        passwordField.removeClass("is-valid").removeClass("is-invalid");
    }
}

function validateLength(password) {
    const tooShort = password.length < 8;
    if (tooShort) {
        showErrors(['Password must be at least 8 characters long']);
        $("#password").addClass("is-invalid").removeClass("is-valid");
        $(".password-strength").css("display", "block");
    }
    return !tooShort;
}

function validateStrength(password) {
    const failed = [];
    let passedMatches = 0;

    for (const check of strengthChecks) {
        if (check.test(password)) {
            passedMatches++;
        } else {
            failed.push(check.message);
        }
    }

    updateStrengthItems(passedMatches);
    showErrors(failed);
}

function showErrors(messages) {
    const errorList = $(".password-errors");
    errorList.empty();
    messages.forEach((msg) => errorList.append($('<li>').text(msg)));
}

function updateStrengthItems(passedMatches) {
    $(".password-strength").css("display", "block");
    const dataIndex = Math.max(passedMatches - 1, 0);
    const color = colors[dataIndex];
    const items = $(".password-strength-item");

    // Reset all to grey first, then colour the active ones
    items.css("background-color", "#e0e0e0");
    for (let i = 0; i < passedMatches; i++) {
        items.eq(i).css("background-color", color);
    }

    $(".password-status").text(statuses[dataIndex]).css("color", color);
}

function resetStrengthValidation() {
    $(".password-strength").css("display", "none");
    $(".password-strength-item").css("background-color", "#e0e0e0");
    $(".password-status").text("").css("color", "");
    $(".password-errors").empty();
}