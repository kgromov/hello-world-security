/*$("#password").change(() => {
    validatePassword();
});*/

$("#password").keyup(() => validatePassword());

// $("#password").change(() => setTimeout(() => {
//         validatePassword();
//     }, 200)
// );

const colors = ['darkred', 'orangered', 'orange', 'yellowgreen'];
const statuses = ['Poor', 'Weak', 'Average', 'Good'];

$(".strength-summary").text("Loaded!!!");

function validatePassword() {
    let passwordField = $("#password");
    const password = passwordField.val();
    resetStrengthValidation();
    if (password) {
        if (validateLength(password)) {
            passwordField.removeClass("is-invalid");
            passwordField.addClass("is-valid");
            validateStrength(password);
        }
    } else {
        passwordField.removeClass("is-valid");
        passwordField.removeClass("is-invalid");
    }
}

function validateLength(password) {
    const minLengthFailed = password.length < 8;
    if (minLengthFailed) {
        const summary = $(".strength-summary");
        summary.text("Password must be at least 8 characters long");
        summary.css("color", "red");
        $("#password").addClass("is-invalid");
    }
    return !minLengthFailed;
}

function validateStrength(password) {
    const regex = /[$-/:-?{-~!"^_@`\[\]]/g;
    const lowerLetters = /[a-z]+/.test(password);
    const upperLetters = /[A-Z]+/.test(password);
    const numbers = /[0-9]+/.test(password);
    const specialChars = regex.test(password);

    const checks = [lowerLetters, upperLetters, numbers, specialChars];

    let passedMatches = 0;
    for (const check of checks) {
        passedMatches += check === true ? 1 : 0;
    }
    updateStrengthItems(passedMatches);
}

function updateStrengthItems(passedMatches) {
    const dataIndex = Math.max(passedMatches - 1, 0);
    const color = colors[dataIndex];
    const items = $(".strength-item");
    for (let i = 0; i < dataIndex + 1; i++) {
        items.eq(i).css("background-color", color);
    }
    const status = $(".status");
    status.text(statuses[dataIndex]);
    status.css("color", color);
}

function resetStrengthValidation() {
    const summary = $(".strength-summary");
    summary.text("");
    summary.css("color", "");

    const items = $(".strength-item");
    for (let i = 0; i < items.length; i++) {
        items.eq(i).css("background-color", "");
        items.eq(i).css("color", "");
    }

    const status = $(".status");
    status.text("");
    status.css("color", "");
}