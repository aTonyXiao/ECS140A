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


;;edit here
;group that takes arguments: a list l and a number n. It returns a new list in which the elements of l are grouped into sublists of length n. The remainder is put in a final sublist.
(defun group(l n)
  (setf count(floor(/ (list-length l) n)))
  (setf begin 0)
  (setf end 0)
  (setf res '())
  (groupHelp count begin end res l n)
  )

(defun groupHelp(count begin end res l n)
  (loop for i from 1 to count
    do (setf end (+ begin n))
    do (setf res (append res (list(subseq l begin end))))
    do (setf begin (+ begin n)))
  (if (not (null (subseq l end (length l))))
    (setf res (append res (list (subseq l end (length l))))))
res
)

; (if (not (null (subseq lista end (length lista))))
;   ((setf res (append res (lista (subseq lista end (length lista)))))))

; res)


;;;; edit here 
(defun mostn(f l)
  (setf num (reduce fun max (mapcar f l)))
  (setf res '())

  (loop for i in l
    do (if (= m (funcall f i)))
      (setf res (append res (list i))))
  (setf res (append (list res)(list m))))


