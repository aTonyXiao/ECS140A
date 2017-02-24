;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Your solution goes in this file ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;all-length, that takes a list and counts the number of atoms that occur in a list at all levels.
(defun all-length(lista)
  (cond
    ((null lista) 0)
    ((atom lista) 1)
    (T (+ (all-length (car lista)) (all-length (cdr lista))))))


;range that takes a list of numbers (with at least one element) and returns a list of length 2 of the smallest and largest numbers.
(defun range(lista)
  (list (apply 'min lista) (apply 'max lista))) ;http://www.programcreek.com/2010/10/lisp-code-find-minimum-element-in-a-list/


;before that searches a list and returns a list of all elements in between the first two arguments (inclusive).
(defun before(begin end lista)
  (cond
    ((null lista) nil);empty list
    ((equal begin (car lista)) (cons begin (getend end (cdr lista))))
    (T (before begin end (cdr lista)))))

(defun getend(end lista)
  (cond
    ((null lista) nil)
    ((equal end (car lista)) (list end))
    (T (cons (car lista) (getend end (cdr lista))))))
