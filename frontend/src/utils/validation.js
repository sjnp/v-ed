const REGEX_EMAIL = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
const REGEX_NAME = /^(?=.{1,50}$)[a-z]+(?:['_.\s][a-z]+[^-\s]*)*$/
const REGEX_PASSWORD = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,24}$/
const REGEX_NUMBER = /[0-9]+/
const REGEX_SYMBOL = /^(?=.{1,50}$)[a-z]+(?:['_.\s][a-z]+[^-\s]*)*$/

const isLength = (data) => data.length > 0

const isNumber = (data) => REGEX_NUMBER.test(data)

const isSpace = (data) => data.indexOf(' ') >= 0 ? true : false

const isSymbol = (data) => !REGEX_SYMBOL.test(data)

const isEmail = (data) => REGEX_EMAIL.test(data)

const isLengthBetween = (data, min, max) => data.length >= min && data.length <= max  

const isEqual = (data1, data2) => data1 === data2

const isMediumPassword = (data) => REGEX_PASSWORD.test(data)

const isName = (data) => REGEX_NAME.test(data)

const validateName = (name, value) => {

    if (!isLength(value)) return `${name} is required.`
    
    if (isNumber(value)) return `${name} can't number.`
    
    if (isSpace(value)) return `${name} can't space bar.`
    
    if (isSymbol(value)) return `${name} can't symbol.`
    
    if (isName(value)) return ''
    
    return '!!! error !!!'
}

const validateFirstname = (firstname) => {
    const name = 'First name'
    return validateName(name, firstname)
}

const validateLastname = (lastname) => {
    const name = 'Last name'
    return validateName(name, lastname)
}

const validateEmail = (email) => {

    if (!isLength(email)) return 'E-Mail address is required.'
    
    if (isNumber(email.charAt(0))) return `E-Mail can't begin with number.` 
    
    if (isEmail(email)) {
        return ''
    } else {
        return 'E-Mail address format invalid'
    }
}

const validatePassword = (password) => {

    const min = 8, max = 24
    
    if (!isLength(password)) return `Password is required.`
    
    if (!isLengthBetween(password, min, max)) {
        return `Password must length between ${min} to ${max} character.`
    } 
    
    if (isMediumPassword(password)) {
        return ''
    } else {
        return 'Password must contain A-Z a-z 0-9 at least 1 character.'
    }
}

const validateConfirmPassword = (password, confirmPassword) => {
    
    const min = 8, max = 24

    if (!isLength(confirmPassword)) return 'Confirm passowrd is required.'

    if (!isLengthBetween(confirmPassword, min, max)) {
        return `Confirm password must length between ${min} to ${max} character.`
    } 

    if (isMediumPassword(confirmPassword)) {
        
        if (isEqual(password, confirmPassword)) {
            return ''
        } else {
            return 'Password not match.'
        }

    } else {
        return 'Confirm password must contain A-Z a-z 0-9 at least 1 character.'
    }
}

export const validation = {
    validateFirstname,
    validateLastname,
    validateEmail,
    validatePassword,
    validateConfirmPassword
}