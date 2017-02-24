;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Your solution goes in this file ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;Tony Xiao Feb.23rd;;;;;;;;;;;;
;;;;;;;;;;;;;;;Part 1;;;;;;;;;;;;;;;;;;
;all-length, that takes a list and counts the number of atoms that occur in a list at all levels.
; (readable:enable-basic-curly)\
; (set-syntax-from-char #\{ #\( )
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

;split-if that returns a list into two parts. It takes two arguments: a function (f) and a list. All members for which f is true go into one list, and the rest go into another list.
(defun split-if (func lista)
  (list (splitrue func lista) (splitfalse func lista)))

(defun splitrue(func lista)
  (cond
    ((null lista) nil)
    ((funcall func (car lista)) (cons (car lista) (splitrue func (cdr lista))))
    (T (splitrue func (cdr lista)))))

(defun splitfalse(func lista)
  (cond
    ((null lista) nil)
    ((not (funcall func (car lista))) (cons (car lista) (splitfalse func (cdr lista))))
    (T (splitfalse func (cdr lista)))))

;group that takes arguments: a list l and a number n. It returns a new list in which the elements of l are grouped into sublists of length n. The remainder is put in a final sublist.
(defun group(lista num)
  (let (count (floor (/ (list-length lista) num))))
  (let (begin 0))
  (let (end 0))
  (let res '())
  (groupHelp count begin end res lista num)
  (if (not (null (subseq lista end (length lista))))
    (setq res (append res (list (subseq lista end (length lista))))))

    res
  )

(defun groupHelp(count begin end res lista num)
  (loop for i from 1 to count
    do (setq end (+ begin num))
    do (setq res (append res (list(subseq lista begin end))))
    do (setq begin (+ begin num))))
