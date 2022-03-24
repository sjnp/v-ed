const REGEX_EMAIL = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
const REGEX_NAME = /^(?=.{1,50}$)[a-z]+(?:['_.\s][a-z]+[^-\s]*)*$/
const REGEX_PASSWORD = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,24}$/

const validateFirstname = (firstname) => {

    if (firstname.length === 0) {
        return `First name is required.`
    } else if (REGEX_NAME.test(firstname)) {
        return ''
    } else {
        if (firstname.length === 0) {
            return `First name is required.`
        } else if (firstname.indexOf(' ') >= 0) {
            return `First name is not space bar.`
        } else if (!/^\d/.test(firstname)) {
            return `First name can't have number.`
        }
    }
}

const validateLastname = (lastname) => {
    if (lastname.length === 0) {
        return `Last name is required.`
    } else if (REGEX_NAME.test(lastname)) {
        return ''
    } else {
        if (lastname.indexOf(' ') >= 0) {
            return `Last name is not space bar.`
        } else if (!/^\d/.test(lastname)) {
            return `Last name can't have number.`
        }
    }
}

const validateEmail = (email) => {
    if (email.length === 0) {
        return `E-Mail address is required`
    } else if (REGEX_EMAIL.test(email)) {
        return ''
    } else {
        if (!REGEX_EMAIL.test(email)) {
            return `Format of e-mail address invalid`
        }
    }
}

const validatePassword = (password) => {
    if (password.length === 0) {
        return `Password is required`
    } else if (REGEX_PASSWORD.test(password)) {
        return ''
    } else {
        if (password.length < 8 || password.length > 24) {
            return `Password must length between 8 to 24 character`
        } else if (!REGEX_PASSWORD.test(password)) {
            return 'Password must contain A-Z a-z 0-9 at least 1 character'
        }
    }
}

const validateConfirmPassword = (password, confirmPassword) => {
    if (confirmPassword.length === 0) {
        return `Confirm password is required`
    } else if (REGEX_PASSWORD.test(confirmPassword)) {
        if (password !== confirmPassword) {
            return `Password don't match`
        } else {
            return ''
        }
    } else {
        if (confirmPassword.length < 8 || confirmPassword.length > 24) {
            return `Password must length between 8 to 24 character`
        } else if (!REGEX_PASSWORD.test(confirmPassword)) {
            return 'Password must contain A-Z a-z 0-9 at least 1 character'
        }
    }
}

export const validation = {
    validateFirstname,
    validateLastname,
    validateEmail,
    validatePassword,
    validateConfirmPassword
}